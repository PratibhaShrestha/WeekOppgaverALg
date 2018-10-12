package no.oslomet.oppgave2;

/////////// DobbeltLenketListe ////////////////////////////////////

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


        // For Demo Purposes
        @Override
        public String toString() {
            String retVal = "( ";
            retVal += (forrige != null) ? forrige.verdi : "HODE";
            retVal += " <- ";
            retVal += (verdi != null) ? verdi : " null ";
            retVal += " -> ";
            retVal += (neste != null) ? neste.verdi : "HALE";
            return retVal + " )";
        }

        void nullstill() {
            forrige = null;
            neste = null;
            verdi = null;
        }
    } // Node

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks) {
        boolean isFromHead = (indeks < (antall / 2));

        if (indeks == 0) return hode; // hvis først
        if (indeks == antall - 1) return hale; // hvis siste

        Node current_node = isFromHead ? hode : hale;
        for (int i = 1; i <= indeks; i++) {
            if (isFromHead)
                current_node = current_node.getNeste();
            else {
                if (antall - indeks == i) break;
                current_node = current_node.getForrige();
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
        endringer = 0;
    }

    private void tomVerdier() {
        hode = hale = null;
        antall = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a) {

        Objects.requireNonNull(a, "Tabellen a er null!");
        a = removeAllNulls(a);

        Node[] nodes = new Node[a.length];
        for (int i = 0; i < a.length; i++) {
            if (i == 0) {
                nodes[i] = new Node(a[i], null, null);
                hale = hode = nodes[i];
            } else if (i == 1 && hode != null) {
                nodes[i] = new Node(a[i], hode, null);
                hale = nodes[i];
                hode.setNeste(nodes[i]);
            } else {
                nodes[i] = new Node(a[i], nodes[i - 1], null);
                nodes[i - 1].setNeste(nodes[i]);
                hale = nodes[i];
            }
            antall++;
            endringer++;
        }

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

        Node newNode = null;
        if (antall == 0) {// hvis der er første eller liste er tom
            newNode = new Node(verdi, null, null);
            hale = hode = newNode;
        } else if (indeks == antall) { //hvis det er siste node
            Node lastNode = hale;
            newNode = new Node(verdi, lastNode, null);
            hale = newNode;
            lastNode.setNeste(hale);
        } else {
            Node nodeAtIndeks = finnNode(indeks);
            Node previousNode = (nodeAtIndeks.equals(hode)) ? null : finnNode(indeks - 1);
            newNode = new Node(verdi, previousNode, nodeAtIndeks);
            nodeAtIndeks.setForrige(newNode);
            newNode.setForrige(previousNode);
            if (nodeAtIndeks.equals(hode)) { // hvis det er aller først !
                hode = newNode;
            } else {
                previousNode.setNeste(newNode);
            }

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
        return (T) thisNode.getVerdi();
    }

    @Override
    public int indeksTil(T verdi) {
        if (antall < 0 || verdi == null) return -1;
        Node current_node = hode;
        int index = -1;
        for (int i = 0; i < antall; i++) {
            if (current_node.getVerdi().equals(verdi)) {
                index = i;
                break;
            }
            current_node = current_node.getNeste();
        }
        return index;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        if (nyverdi == null)
            throw new NullPointerException("ikke tilatt med nullverdi!");
        indeksKontroll(indeks, false);
        Node thisNode = finnNode(indeks);
        T gammelVerdi = (T) thisNode.getVerdi();
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
        T retVerdi;
        if (indeks == 0) {
            // fiks hode
            if (hode.getNeste() != null) {
                Node nextNode = hode.getNeste();
                hode = nextNode;
                hode.setForrige(null);
            }
        } else if (indeks == antall - 1) {
            Node previousNode = hale.getForrige();
            hale = previousNode;
            hale.setNeste(null);
            //fiks hale
        } else {
            Node nodeAfterIndeks = finnNode(indeks + 1);
            Node nodeBeforeIndeks = finnNode(indeks - 1);
            nodeBeforeIndeks.setNeste(nodeAfterIndeks);
            nodeAfterIndeks.setForrige(nodeBeforeIndeks);
        }

        antall--;
        endringer++;

        if (antall == 0) hode = hale = null;

        retVerdi = (T) thisNode.verdi;
        thisNode.nullstill();
        return retVerdi;

    }

    @Override
    public void nullstill() {
        Node current_node = hode, next_node;
        while (current_node.getNeste() != null) {
            next_node = current_node.getNeste();
            current_node.setNeste(null);
            current_node = next_node;
        }

        while (current_node.getForrige() != null) {
            next_node = current_node.getForrige();
            current_node.setForrige(null);
        }
        endringer++;
        tomVerdier();
    }

    @Override
    public String toString() {
        Node current_node = hode;
        if (current_node == null) return "[]";
        boolean harAndreVerdi = false;
        StringBuilder str = new StringBuilder("[");
        do {
            str.append(current_node.getVerdi()).append(", ");
            //System.out.println(current_node); // FOR DEBUG PURPOSE
            harAndreVerdi = true;
        } while ((current_node = current_node.getNeste()) != null);

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
        do {
            str.append(current_node.getVerdi()).append(", ");
            //System.out.println(current_node); // FOR DEBUG PURPOSE
            harAndreVerdi = true;
        } while ((current_node = current_node.getForrige()) != null);

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
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        return new DobbeltLenketListeIterator(indeks);
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
            denne = hode;
            fjernOK = false;
            iteratorendringer = endringer;

            indeksKontroll(indeks, false);
            for (int i = 0; i < indeks; i++) {
                denne = denne.getNeste();
            }
        }

        @Override
        public boolean hasNext() {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next() {
            if (iteratorendringer != endringer)
                throw new ConcurrentModificationException("ulik endringer verdier");
            if (!hasNext()) throw new NoSuchElementException();

            T tmp = denne.verdi;
            denne = denne.getNeste();
            fjernOK = true;
            return tmp;

        }

        @Override
        public void remove() {
            if (iteratorendringer != endringer)
                throw new ConcurrentModificationException("ulik endringer verdier");
            if (!fjernOK) throw new IllegalStateException();
            fjernOK = false;

            if (antall == 1)
                hode = hale = null;
            else if (denne == null) {
                hale = hale.getForrige();
                hale.setNeste(null);
            } else if (denne.forrige == hode) {
                hode = hode.getNeste();
                hode.setForrige(null);
            } else {
                //removing the denne.forrige
                Node previousNode = denne.getForrige();
                Node beforePreviousNode = previousNode.getForrige();
                beforePreviousNode.setNeste(denne);
                denne.setForrige(beforePreviousNode);
                previousNode.nullstill();
            }

            antall--;
            endringer++;
            iteratorendringer++;
        }

    } // DobbeltLenketListeIterator

} // DobbeltLenketListe
