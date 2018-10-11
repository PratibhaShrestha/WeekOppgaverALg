package no.oslomet.oppgave2;

public class Oppgave2 {

    public static void main(String[] args) {

        Character[] c = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);

        System.out.println(liste.inneholder(null));

//        System.out.println(liste.subliste(0,11));// skal kaste unntak

    }

}
