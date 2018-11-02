package no.oslomet.oblig3;


////////////////// ObligSBinTre /////////////////////////////////

import no.oslomet.oblig3.hjelpeklasser.Beholder;

import java.util.*;

public class ObligSBinTre<T> implements Beholder<T> {

    private static final class Node<T> { // en indre nodeklasse

        private T verdi; // nodens verdi
        private Node<T> venstre, høyre; // venstre og høyre barn
        private Node<T> forelder; // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder) // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return " " + verdi;
            /*
            // FOR DEMO PURPOSES
            T value = null, left = null, right = null, parent = null;

            if (venstre != null) left = venstre.verdi;
            if (forelder != null) parent = forelder.verdi;
            if (verdi != null) value = verdi;
            if (høyre != null) right = høyre.verdi;

            StringBuilder str = new StringBuilder();

            str.append(String.format(" Node@%-8s [ %5s", Integer.toHexString(hashCode()), left));
            str.append(String.format(" <--  Node@%-8s ^ %-4s %3s -->",
                    (forelder != null) ? Integer.toHexString(forelder.hashCode()) : "", parent, value));
            str.append(String.format(" %-5s ]", right));

            return str.toString();
            */

        }
    } // class Node

    private Node<T> rot; // peker til rotnoden
    private int antall; // antall noder
    private int endringer; // antall endringer
    private final Comparator<? super T> comp; // komparator

    // konstruktør
    public ObligSBinTre(Comparator<? super T> c) {
        rot = null;
        antall = 0;
        comp = c;
    }

    @Override
    public final boolean leggInn(T verdi)    // skal ligge i class SBinTre
    {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;
            cmp = comp.compare(verdi, p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<T>(verdi, q);                // oppretter en ny node

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                      // høyre barn til q

        antall++;                              // en verdi mer i treet
        endringer++;
        return true;                             // vellykket innlegging
    }

    @Override
    public boolean inneholder(T verdi) {
        if (verdi == null) return false;
        Node<T> p = rot;
        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }
        return false;
    }

    @Override
    public boolean fjern(T verdi)  // hører til klassen SBinTre
    {
        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> p = rot, q = null;   // q skal være forelder til p

        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi, p.verdi);      // sammenligner
            if (cmp < 0) {
                q = p;
                p = p.venstre;
            }      // går til venstre
            else if (cmp > 0) {
                q = p;
                p = p.høyre;
            }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi

        if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
            if (b != null) {
                b.forelder = q;
            }

        } else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null) {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
            r.forelder = p.forelder;
        }

        antall--;   // det er nå én node mindre i treet
        endringer--;
        return true;
    }

    public int fjernAlle(T verdi) {
        int occurance = 0;

        Boolean hasDeleted;

        do {
            hasDeleted = false;
            Node<T> p = rot;
            while (p != null) {
                int cmp = comp.compare(verdi, p.verdi);
                if (cmp == 0) {
                    occurance++;
                    hasDeleted = fjern(verdi);
                }
                p = cmp < 0 ? p.venstre : p.høyre;
            }
        } while (hasDeleted);


        return occurance;
    }

    @Override
    public int antall() {
        return antall;
    }

    public int antall(T verdi) {
        int occurance = 0;
        Node<T> p = rot;
        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp == 0) occurance++;
            p = cmp < 0 ? p.venstre : p.høyre;

        }
        return occurance;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }


    @Override
    public void nullstill() {
        Node<T> curr = rot;
        if (curr != null) {
            nullstillNode(curr);
        }

        antall = 0;
        endringer = 0;
        rot = null;
    }

    void nullstillNode(Node node) {
        if (node != null) {
            nullstillNode(node.venstre);
            node.verdi = null;
            node.verdi = null;
            nullstillNode(node.høyre);
            node.høyre = null;
            node.forelder = null;
        }
    }

    private static <T> Node<T> nesteInorden(Node<T> p) {
        if (p == null)
            return null;
        Node current = p;

        // case 1: if the tree has right child / right subtree
        if (current.høyre != null) {
            current = current.høyre;
            while (current.venstre != null) current = current.venstre;
            return current;
        }

        //case 2: if there is no right subtree! The node to the last left turn!
        Node parent = p.forelder;
        while (parent != null && p == parent.høyre) {
            p = parent;
            parent = parent.forelder;
        }
        return parent;
    }

    /* HELPER METHODS */
    private Node lavesteVerdiInorden(Node node) {
        Node current = node;
        if (current != null) {
            while (current.venstre != null) current = current.venstre;
        }
        return current;
    }

    private String printLeaves(Node node, StringBuilder strOutput) {
        if (node == null) return null;

        if (node.venstre == null && node.høyre == null) {
            strOutput.append(node.verdi + ", ");
        } else {
            printLeaves(node.venstre, strOutput);
            printLeaves(node.høyre, strOutput);
        }
        return strOutput.toString();
    }

    // LEAVES VALUES USING THE ARRAYDEQUE ( WITHOUT RECURSION)
    // NOT USED IN THIS OPPGAVE ..
    public String leavesValue() {
        Deque<Node> stack = new ArrayDeque<>();
        Deque<Node> bladnodeStack = new ArrayDeque<>();
        if (rot == null) return "[]";
        else stack.addFirst(rot);

        while (!stack.isEmpty()) {
            Node current = stack.removeFirst();
            if (current.høyre != null) stack.addFirst(current.høyre);
            if (current.venstre != null) stack.addFirst(current.venstre);
            if (current.høyre == null && current.venstre == null) bladnodeStack.addLast(current);
        }

        StringBuilder str = new StringBuilder();
        str.append("[");
        for (Node node : bladnodeStack) {
            str.append(node.verdi + ", ");
        }
        str.delete(str.length() - 2, str.length());
        return str.toString() + "]";
    }

    @Override
    public String toString() {
        boolean isEmpty = true;
        StringBuilder strOutput = new StringBuilder();
        strOutput.append("[");
        Node<T> p = lavesteVerdiInorden(rot);
        for (; p != null; p = nesteInorden(p)) {
            strOutput.append(p.verdi).append(", ");
            isEmpty = false;
        }
        if (!isEmpty)
            strOutput.delete(strOutput.length() - 2, strOutput.length());

        strOutput.append("]");

        return strOutput.toString();
    }

    public String omvendtString() {
        boolean isEmpty = true;
        StringBuilder strOutput = new StringBuilder();
        strOutput.append("[");

        List<T> result = new ArrayList<>();
        Deque<Node> stack = new ArrayDeque<>();
        Node current = null;
        if (rot == null) return "[]";
        else current = rot;

        while (!stack.isEmpty() || current != null) {
            if (current != null) {
                stack.push(current);
                current = current.høyre;
            } else {
                Node node = stack.pop();
                result.add((T) node.verdi);  // Add after all left children
                current = node.venstre;
            }
        }

        for (T value : result) {
            strOutput.append(value).append(", ");
            isEmpty = false;
        }

        if (!isEmpty)
            strOutput.delete(strOutput.length() - 2, strOutput.length());

        strOutput.append("]");

        return strOutput.toString();
    }

    public String høyreGren() {

        Deque<Node> stack = new ArrayDeque<>();
        StringBuilder str = new StringBuilder();
        str.append("[");

        if (rot == null) return "[]";
        else str.append(rot.verdi + ", ");

        if (rot.høyre != null)
            stack.addFirst(rot.høyre);
        else if (rot.venstre != null) { // if the høyre is null, take the left as right ! as question
            stack.addFirst(rot.venstre);
        }

        while (!stack.isEmpty()) {
            Node current = stack.removeFirst();
            if (current.høyre != null) stack.addFirst(current.høyre);
            str.append(current.verdi + ", ");
            if (current.venstre != null) stack.addFirst(current.venstre);
        }

        str.delete(str.length() - 2, str.length());
        return str.toString() + "]";
    }

    public String lengstGren() {

        Deque<Node> stack = new ArrayDeque<>();
        if (rot == null) return "[]";
        stack.add(rot);
        Node lastNode = rot;
        while (!stack.isEmpty()) {
            lastNode = stack.removeFirst();
            if (lastNode.høyre != null) stack.addLast(lastNode.høyre);
            if (lastNode.venstre != null) stack.addLast(lastNode.venstre);
        }

        //Now we have the lastNode... so traverse back
        StringBuilder str = new StringBuilder();
        str.append("[" + rot.verdi + ", ");
        while (lastNode != rot) {
            str.insert(4, lastNode.verdi + ", ");
            lastNode = lastNode.forelder;
        }
        str.delete(str.length() - 2, str.length());
        return str.toString() + "]";
    }

    private List<String> printAllRootToLeafPaths(Node node, StringBuilder path, List sw) {
        if (node == null) return null;
        path.append(String.valueOf(node.verdi) + ":");
        if (node.venstre == null && node.høyre == null) {
            sw.add(path.toString());
        } else {
            printAllRootToLeafPaths(node.venstre, new StringBuilder(path.toString()), sw);
            printAllRootToLeafPaths(node.høyre, new StringBuilder(path.toString()), sw);
        }
        return sw;
    }

    public String[] grener() {
        List<String> sw = new ArrayList<>();
        StringBuilder path = new StringBuilder();
        List<String> allPaths = printAllRootToLeafPaths(rot, path, sw);
        String[] array = new String[0];
        if (allPaths != null) {
            array = allPaths.toArray(new String[0]);
            for (int i = 0; i < array.length; i++) {
                array[i] = "[" + array[i].replaceAll(":", ", ");
                // removing the last ,
                array[i] = array[i].substring(0, array[i].length() - 2) + "]";
            }
        }
        return array;
    }

    public String bladnodeverdier() {
        String strOutput = "[";
        String strLeaves = printLeaves(rot, new StringBuilder());
        if (strLeaves != null) {
            strOutput += strLeaves.substring(0, strLeaves.length() - 2);
        }
        strOutput += "]";
        return strOutput;
    }

    public String postString() {
        Stack<Node> stack = new Stack<>();
        List<T> list = new ArrayList<>();
        if (rot == null) return "[]";
        stack.push(rot);
        Node prev = null;
        while (!stack.isEmpty()) {
            Node current = stack.peek();
            if (prev == null || prev.venstre == current ||
                    prev.høyre == current) {
                if (current.venstre != null) stack.push(current.venstre);
                else if (current.høyre != null) stack.push(current.høyre);
                else {
                    stack.pop();
                    list.add((T) current.verdi);
                }
            } else if (current.venstre == prev) {
                if (current.høyre != null) stack.push(current.høyre);
                else {
                    stack.pop();
                    list.add((T) current.verdi);
                }
            } else if (current.høyre == prev) {
                stack.pop();
                list.add((T) current.verdi);
            }
            prev = current;
        }
        return String.valueOf(list);
    }

    @Override
    public Iterator<T> iterator() {
        return new BladnodeIterator();
    }

    private class BladnodeIterator implements Iterator<T> {
        private Node<T> p = rot, q = null;
        private boolean removeOK = false;
        private int iteratorendringer = endringer;

        private BladnodeIterator() // konstruktør
        {
            if (rot != null) {
                p = lavesteVerdiInorden(rot);
                iteratorendringer = endringer;
            }
        }

        @Override
        public boolean hasNext() {
            return p != null; // Denne skal ikke endres!
        }

        @Override
        public T next() {
            if (iteratorendringer != endringer)
                throw new ConcurrentModificationException("ulik endringer verdier");
            if (!hasNext())
                throw new NoSuchElementException("Det ikke er flere bladnoder igjen.");

            while (p != null) {
                if (p.venstre == null && p.høyre == null) {
                    q = p;
                    p = nesteInorden(p);
                    break;
                } else
                    p = nesteInorden(p);
            }
            removeOK = true;
            return q.verdi;
        }

        @Override
        public void remove() {
            if (iteratorendringer != endringer)
                throw new ConcurrentModificationException("ulik endringer verdier");
            if (!removeOK) throw new IllegalStateException();
            if (q == rot) { // if this is the root, then everything is null !
                rot = p = q = null;
                removeOK = false;
                antall--;
                endringer++;
                iteratorendringer++;
                return;
            }

            // else , set the respective node to be null !
            if (q.forelder.høyre == q) q.forelder.høyre = null;
            else q.forelder.venstre = null;

            if (p != null && nesteInorden(p) == null) {
                while (p != null) {
                    if (p.venstre != null) p = p.venstre;
                    else p = p.høyre;
                }
            }
            removeOK = false;
            antall--;
            endringer++;
            iteratorendringer++;
        }
    } // BladnodeIterator
} // ObligSBinTre