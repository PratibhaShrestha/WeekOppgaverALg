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
            return "" + verdi;
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
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi, p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<T>(verdi, q);                // oppretter en ny node

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q

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


    //    DELETE FROM HERE !!!

    /**
     * Print a representation of this BST on System.out.
     */
    public void print() {
        printHelper(rot, "");
    }

    /**
     * Print the BST rooted at root, with indent preceding all output lines.
     * The nodes are printed in in-order.
     *
     * @param root   The root of the tree to be printed.
     * @param indent The string to go before output lines.
     */
    private static void printHelper(Node root, String indent) {
        if (root == null) {
            System.out.println(indent + "null");
            return;
        }

        // Pick a pretty indent.
        String newIndent;
        if (indent.equals("")) {
            newIndent = ".. ";
        } else {
            newIndent = "..." + indent;
        }

        printHelper(root.venstre, newIndent);
        System.out.println(indent + root.verdi);
        printHelper(root.høyre, newIndent);
    }

    void display() {

        Node root = rot;
        if (root != null) {
            display(root.venstre);
            display(root.høyre);
        }
    }

    private void display(Node root) {
        if (root != null) {
            display(root.venstre);
            System.out.print(" " + root.verdi);
            display(root.høyre);
        }
    }
    //    DELETE UPTO HERE !!!

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> nesteInorden(Node<T> p) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String omvendtString() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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