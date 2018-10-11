package no.oslomet.oppgave2;

public class Oppgave2 {

    public static void main(String[] args) {

        DobbeltLenketListe<String> sliste = new DobbeltLenketListe<>();
        sliste.leggInn("A");

        System.out.println(sliste.indeksTil(new String("A")));

        if (sliste.indeksTil(new String("A")) != 0) {
            System.out.println("Oppgave 4a: Stygg feil! Du MÅ bruke equals");
            System.out.println("            og ikke == i sammenligningen i indeksTil()!");
        }

    }

}
