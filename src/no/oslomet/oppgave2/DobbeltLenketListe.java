package no.oslomet.oppgave2;

/////////// DobbeltLenketListe ////////////////////////////////////

import no.oslomet.cs.algdat.Week6;

import java.lang.reflect.Array;
import java.util.*;

public class DobbeltLenketListe<T> implements Liste<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }

        // @formatter:off
        //GETTERS
        public T getVerdi() { return verdi; }
        public Node<T> getForrige() { return forrige; }
        public Node<T> getNeste() { return neste; }

        // SETTERS
        public void setVerdi(T verdi) { this.verdi = verdi; }
        public void setForrige(Node<T> forrige) { this.forrige = forrige; }
        public void setNeste(Node<T> neste) { this.neste = neste; }
        // @formatter:on
        
       /*
       // For Demo Purposes
       @Override
        public String toString() {
            String retVal = "( ";
            retVal += (forrige != null) ? forrige.verdi : (verdi == null) ? "HODE" : " null ";
            retVal += " <- ";
            retVal += (verdi != null) ? verdi : " null ";
            retVal += " -> ";
            retVal += (neste != null) ? neste.verdi : (verdi == null) ? "HALE" : " null ";
            return retVal + " )";
        }*/
    } // Node

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    // konstruktør
    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a) {

        hode = new Node<T>(null, null, null);
        hale = new Node<T>(null, hode, null);
        hode.setNeste(hale);

        Objects.requireNonNull(a, "Tabellen a er null!");

        a = removeAllNulls(a);

        Node[] nodes = new Node[a.length];

        for (int i = 0; i < a.length; i++) {
            if (antall == 0) {
                nodes[antall] = new Node<T>(a[i], hode, hale);
                hode.setNeste(nodes[antall]);
            } else {
                nodes[antall] = new Node<T>(a[i], nodes[antall - 1], hale);
            }
            hale.setForrige(nodes[antall]);
            antall++;
            endringer++;
        }
        // Setting the neste...
        for (int i = 0; i < nodes.length - 1; i++) {
            nodes[i].setNeste(nodes[i + 1]);
        }


       /*
       //For Debug purposes..
        System.out.println("\n Printing Node: ");
        System.out.print(hode);
        for (Node node : nodes) {
            System.out.print(node);
        }
        System.out.println(hale);
        System.out.println("Nodes counter: " + nodes.length);
        */

    }

    // for å fjerne null verdier fra en tabell.
    private T[] removeAllNulls(T[] a) {
        T[] temp = (T[]) new Object[a.length];
        int index = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != null)
                temp[index++] = a[i];
        }
        return Arrays.copyOf(temp, index);
    }

    // subliste
    public Liste<T> subliste(int fra, int til) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall <= 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        leggInn(antall(), verdi);
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {

        if (verdi == null)
            throw new IllegalArgumentException("Ulovlig å legge inn null verdier!");
        if (indeks < 0 || indeks > antall)
            throw new IndexOutOfBoundsException("Index" + indeks + "er ulovlig!");

    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T hent(int indeks) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public String toString() {
        Node current_node = hode;
        if (current_node == null) return "[]";

        boolean harAndreVerdi = false;
        String str = " [";
        while (current_node.getNeste() != null) {
            if ((current_node = current_node.getNeste()) == hale) break;
            harAndreVerdi = true;
            str += current_node.getVerdi() + ", ";
        }
        // fjerner den siste Comma med mellomrom
        if (harAndreVerdi)
            str = str.substring(0, str.length() - 2);
        return str + "] ";
    }

    public String omvendtString() {
        Node current_node = hale;
        if (current_node == null) return "[]";

        boolean harAndreVerdi = false;
        String str = " [";
        while (current_node.getForrige() != null) {
            if ((current_node = current_node.getForrige()) == hode) break;
            harAndreVerdi = true;
            str += current_node.getVerdi() + ", ";
        }
        // fjerner den siste Comma med mellomrom
        if (harAndreVerdi)
            str = str.substring(0, str.length() - 2);
        return str + "] ";
    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public boolean hasNext() {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator

} // DobbeltLenketListe
