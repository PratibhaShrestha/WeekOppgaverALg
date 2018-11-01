package no.oslomet.oblig3;


////////////////// ObligSBinTre /////////////////////////////////

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.tools.javac.util.GraphUtils;
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

            //return " " + verdi;


            // FOR DEMO PURPOSES
            T value = null, left = null, right = null, parent = null;

            if (venstre != null) left = venstre.verdi;
            if (forelder != null) parent = forelder.verdi;
            if (verdi != null) value = verdi;
            if (høyre != null) right = høyre.verdi;

            StringBuilder str = new StringBuilder();

            str.append(String.format(" Node@%-8s [ %5d", Integer.toHexString(hashCode()), left));
            str.append(String.format(" <--  Node@%-8s ^ %-4d %3d -->",
                    (forelder != null) ? Integer.toHexString(forelder.hashCode()) : "", parent, value));
            str.append(String.format(" %-5d ]", right));

            return str.toString();

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

        antall++;                                // en verdi mer i treet
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

    static Node lavesteVerdiInorden(Node node) {
        Node current = node;
        if (current != null) {
            while (current.venstre != null) current = current.venstre;
        }
        return current;
    }

    // DELETE FROM HERE !!!

    public void printFromRoot() {
        Node<T> curr = rot;
        if (curr != null) {
            printNode(curr);
        }
    }

    void printNode(Node node) {
        if (node != null) {
            System.out.println(node);
            printNode(node.venstre);
            printNode(node.høyre);
        }
    }

    void printTest() {
        Node<T> curr = rot;

        System.out.println(curr);

        curr = lavesteVerdiInorden(rot);

        System.out.println(curr);

        curr = nesteInorden(curr);

        System.out.println(curr);
    }
    // DELETE UPTO HERE !!!


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

        /*
        // Inorder Traversal Using ArrayDeque
        boolean isEmpty = true;
        StringBuilder strOutput = new StringBuilder();
        strOutput.append("[");

        List<T> result = new ArrayList<>();
        Deque<Node> stack = new ArrayDeque<>();
        Node p = rot;

        while (!stack.isEmpty() || p != null) {
            if (p != null) {
                stack.push(p);
                p = p.venstre;
            } else {
                Node node = stack.pop();
                result.add((T) node.verdi);  // Add after all left children
                p = node.høyre;
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
         */
    }

    public String omvendtString() {
        boolean isEmpty = true;
        StringBuilder strOutput = new StringBuilder();
        strOutput.append("[");

        List<T> result = new ArrayList<>();
        Deque<Node> stack = new ArrayDeque<>();
        Node p = rot;

        while (!stack.isEmpty() || p != null) {
            if (p != null) {
                stack.push(p);
                p = p.høyre;
            } else {
                Node node = stack.pop();
                result.add((T) node.verdi);  // Add after all left children
                p = node.venstre;
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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String lengstGren() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String[] grener() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String bladnodeverdier() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String postString() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

        @Override
        public boolean hasNext() {
            return p != null; // Denne skal ikke endres!
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }
    } // BladnodeIterator
} // ObligSBinTre