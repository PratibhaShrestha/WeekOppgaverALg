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
        boolean isFromHead = (indeks < (antall / 2));

        Node current_node = isFromHead ? hode : hale;
        for (int i = 0; i <= indeks; i++) {
            if (isFromHead)
                current_node = current_node.getNeste();
            else {
                current_node = current_node.getForrige();
                if (antall - indeks == i + 1) break;
            }
        }
        return current_node;
    }

    public static void fratilKontroll(int antall, int fra, int til) {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");
        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");
        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    // konstruktør
    public DobbeltLenketListe() {
        tomVerdier();
    }

    private void tomVerdier(){
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
        fratilKontroll(antall, fra, til);
        Liste<T> retListe = new DobbeltLenketListe<T>();
        for (int i = fra; i < til; i++) {
            retListe.leggInn(hent(i));
        }
        return retListe;
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
            throw new NullPointerException("Ulovlig å legge inn null verdier!");
        if (indeks < 0 || indeks > antall)
            throw new IndexOutOfBoundsException("Index " + indeks + " er ulovlig!");

        if (hode == null)
            hode = new Node<T>(null, null, hale);
        if (hale == null)
            hale = new Node<T>(null, hode, null);

        Node newNode = null;
        if (antall == 0) {// hvis der er første eller liste er tom
            newNode = new Node(verdi, hode, hale);
            hode.setNeste(newNode);
            hale.setForrige(newNode);
        } else if (antall == indeks) { // hvis det er på siste
            Node lastNode = hale.getForrige();
            newNode = new Node(verdi, lastNode, hale);
            lastNode.setNeste(newNode);
            hale.setForrige(newNode);
        } else { // hvis det er først eller siste
            Node nodeAtIndeks = finnNode(indeks);
            Node nodeBeforeIndeks = finnNode(indeks - 1);
            newNode = new Node(verdi, nodeBeforeIndeks, nodeAtIndeks);
            nodeBeforeIndeks.setNeste(newNode);
            nodeAtIndeks.setForrige(newNode);
        }
        antall++;
        endringer++;

    }

    @Override
    public boolean inneholder(T verdi) {
        return (indeksTil(verdi) != -1);
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        Node thisNode = finnNode(indeks);
        return (T) thisNode.verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if (antall < 0 || verdi == null) return -1;
        Node current_node = hode;
        int index = -1;
        for (int i = 0; i < antall; i++) {
            current_node = current_node.getNeste();
            if (current_node.verdi.equals(verdi)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        if (nyverdi == null)
            throw new NullPointerException("ikke tilatt med nullverdi!");
        indeksKontroll(indeks, false);
        Node thisNode = finnNode(indeks);
        T gammelVerdi = (T) thisNode.verdi;
        thisNode.verdi = nyverdi;
        endringer++;
        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {

        int indeks = indeksTil(verdi);
        if (indeks == -1)
            return false;
        else {
            fjern(indeks);
            return true;
        }
    }

    @Override
    public T fjern(int indeks) {

        indeksKontroll(indeks, false);

        Node thisNode = finnNode(indeks);
        if (indeks == 0) {
            // fiks hode
            Node nextNode = finnNode(indeks + 1);
            hode.setNeste(nextNode);
            nextNode.setForrige(hode);
        } else if (indeks == antall - 1) {
            Node previousNode = finnNode(indeks - 1);
            previousNode.setNeste(hale);
            hale.setForrige(previousNode);
            //fiks hale
        } else {
            Node nodeAfterIndeks = finnNode(indeks + 1);
            Node nodeBeforeIndeks = finnNode(indeks - 1);
            nodeBeforeIndeks.setNeste(nodeAfterIndeks);
            nodeAfterIndeks.setForrige(nodeBeforeIndeks);
        }

        antall--;
        endringer++;
        if (antall == 0) {
            hode.setNeste(hale);
            hale.setForrige(hode);
        }


        return (T) thisNode.verdi;

    }

    @Override
    public void nullstill() {
        Node current_node = hode, next_node;
        while (current_node.getNeste() != null) {
            next_node = current_node.getNeste();
            current_node.setNeste(null);
            current_node = next_node;
            if (next_node == hale) break;
        }

        while (current_node.getForrige() != null) {
            next_node = current_node.getForrige();
            current_node.setForrige(null);
            if (next_node == hode) break;
        }

        tomVerdier();
    }

    @Override
    public String toString() {
        Node current_node = hode;
        if (current_node == null) return "[]";
        boolean harAndreVerdi = false;
        StringBuilder str = new StringBuilder("[");
        while (current_node.getNeste() != null) {
            if ((current_node = current_node.getNeste()) == hale) break;
            str.append(current_node.getVerdi()).append(", ");
            harAndreVerdi = true;
        }
        // fjerner den siste Comma med mellomrom
        if (harAndreVerdi)
            str = new StringBuilder(str.substring(0, str.length() - 2));
        return str + "]";
    }

    public String omvendtString() {
        Node current_node = hale;
        if (current_node == null) return "[]";

        boolean harAndreVerdi = false;
        StringBuilder str = new StringBuilder("[");
        while (current_node.getForrige() != null) {
            if ((current_node = current_node.getForrige()) == hode) break;
            str.append(current_node.getVerdi()).append(", ");
            harAndreVerdi = true;
        }
        // fjerner den siste Comma med mellomrom
        if (harAndreVerdi)
            str = new StringBuilder(str.substring(0, str.length() - 2));
        return str + "]";
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
