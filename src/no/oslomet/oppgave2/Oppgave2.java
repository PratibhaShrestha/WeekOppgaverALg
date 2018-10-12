package no.oslomet.oppgave2;

import java.util.Iterator;

public class Oppgave2 {

    public static void main(String[] args) {


        for (int i = 0; i <= 4; i++) {
            String[] arr = {"A","B","C","D"};
            DobbeltLenketListe<String> liste = new DobbeltLenketListe<String>(arr);

            liste.leggInn(i,"Z");

            System.out.println("Printing Nodes: ");
            System.out.println(liste.toString());
        }



    }

}
