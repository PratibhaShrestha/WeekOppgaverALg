package no.oslomet.oblig3;


////////////////// ObligSBinTre /////////////////////////////////

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

            return " " + verdi;

            /*
            // FOR DEMO PURPOSES
            T value = null, left = null, right = null, parent = null;

            if (venstre != null) left = venstre.verdi;
            if (forelder != null) parent = forelder.verdi;
            if (verdi != null) value = verdi;
            if (høyre != null) right = høyre.verdi;

            StringBuilder str = new StringBuilder();

            str.append(String.format("[ ^ : %-5d", parent));
            str.append(String.format(" %5d", left));
            str.append(String.format(" <-- %3d -->", value));
            str.append(String.format(" %-5d ]", right));

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
        p.forelder = q;

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
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> nesteInorden(Node<T> p) {
        if (p == null)
            return null;

        if (p.høyre != null) {
            return førstInorden(p.høyre);
        }

        Node parent = p.forelder;
        while (parent != null && p == parent.høyre) {
            p = parent;
            parent = parent.forelder;
        }
        return parent;
    }

    static Node førstInorden(Node node) {
        Node current = node;
        if (current != null)
            while (current.venstre != null) current = current.venstre;
        return current;
    }

    static Node sistInorden(Node node) {
        Node current = node;
        if (current != null)
            while (current.høyre != null) current = current.høyre;
        return current;
    }


    @Override
    public String toString() {
        boolean isEmpty = true;
        StringBuilder strOutput = new StringBuilder();
        strOutput.append("[");
        Node<T> p = førstInorden(rot);
        while (p != null) {
            strOutput.append(p.verdi).append(", ");
            isEmpty = false;
            p = nesteInorden(p);
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