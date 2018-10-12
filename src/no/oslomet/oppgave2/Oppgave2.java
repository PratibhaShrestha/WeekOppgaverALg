package no.oslomet.oppgave2;

import java.util.Iterator;

public class Oppgave2 {

    public static void main(String[] args) {


        String[] arr = {"A","B"};
        DobbeltLenketListe<String> liste = new DobbeltLenketListe<String>(arr);


        System.out.println("Printing Nodes: ");
        System.out.println(liste.toString());

        System.out.println(liste.fjern(1));
        System.out.println(liste);

        System.out.println(liste.fjern(0));
        System.out.println(liste);

    }


}
