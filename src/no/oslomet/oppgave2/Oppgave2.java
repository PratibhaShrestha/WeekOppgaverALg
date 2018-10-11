package no.oslomet.oppgave2;

public class Oppgave2 {

    public static void main(String[] args) {

        Character[] c = {'A', 'B', 'C', 'D', 'E'};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);

        System.out.println(liste);
        System.out.println(liste.fjern(1));
        System.out.println(liste);
        System.out.println(liste.fjern((Character) 'Z'));
        System.out.println(liste);

    }

}
