package no.oslomet.oppgave2;

public class Oppgave2 {

    public static void main(String[] args) {

        DobbeltLenketListe<String> sliste = new DobbeltLenketListe<>();
        sliste.leggInn("A");
        sliste.leggInn("B");
        sliste.leggInn("C");


        System.out.println(sliste);

        sliste.nullstill();

    }

}
