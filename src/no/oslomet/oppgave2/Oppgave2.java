package no.oslomet.oppgave2;

public class Oppgave2 {

    public static void main(String[] args) {

        DobbeltLenketListe<String> liste = new DobbeltLenketListe<>(new String[]{
                "Birger", "Lars", "Anders", "Bodil", "Kari", "Per", "Berit"
        });
        liste.fjernHvis(navn -> navn.charAt(0) == 'B');
        // fjerner navn som starter med B

        System.out.println(liste + "" + liste.omvendtString());
        // Utskrift: [Lars, Anders, Kari, Per] [Per,Kari, Anders, Lars]
    }


}
