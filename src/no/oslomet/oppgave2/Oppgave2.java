package no.oslomet.oppgave2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Oppgave2 {

    public static void main(String[] args) {


        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();

        for (int j = 1; j <= 7; j++) liste.leggInn(j);

        System.out.println(liste);

        Iterator<Integer> i;

        try {
            liste.iterator(7);
            System.out.println("Oppgave 8s: Indeks 7 finnes ikke!");

        } catch (Exception e) {
            if (!(e instanceof IndexOutOfBoundsException)) {
                System.out.println("Oppgave 8t: Metoden kaster feil type unntak!");
            }
        }

        try {
            liste.iterator(-1);
            System.out.println("Oppgave 8u: Indeks -1 finnes ikke!");
        } catch (Exception e) {
            if (!(e instanceof IndexOutOfBoundsException)) {
                System.out.println("Oppgave 8v: Metoden kaster feil type unntak!");
            }
        }

        int m = 4;
        i = liste.iterator(3);
        for (; i.hasNext(); ) {
            if (i.next() != m) {
                System.out.println("Oppgave 8w: Feil i metoden next()!");
            }
            m++;
        }

        try {
            i.next();
            System.out.println("Oppgave 8x: Skal kaste unntak for next() her!");
        } catch (Exception e) {
            if (!(e instanceof NoSuchElementException)) {
                System.out.println("Oppgave 8y: Det kastes feil type unntak!");
            }
        }

    }


}
