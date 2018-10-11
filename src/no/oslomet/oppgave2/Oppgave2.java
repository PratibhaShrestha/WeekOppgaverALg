package no.oslomet.oppgave2;

public class Oppgave2 {

    public static void main(String[] args) {

        Character[] c = {'A', 'B', 'C'};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);

        liste.leggInn(2, 'D');

        System.out.println(liste);

//        System.out.println(liste.subliste(0,11));// skal kaste unntak

    }

}
