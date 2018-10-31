package no.oslomet.oblig3;

import java.util.Comparator;
import java.util.TreeSet;

public class Oblig3 {


    public static void main(String[] args) {

        int[] a = {4, 7, 2, 9, 4, 10, 8, 7, 4, 6, 1};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);
        System.out.println(tre.fjernAlle(4)); // 3

        tre.fjernAlle(7);
        tre.fjern(8);
        System.out.println(tre.antall()); // 5
        System.out.println(tre + " " + tre.omvendtString());

        // [1, 2, 6, 9, 10] [10, 9, 6, 2, 1]
        // OBS: Hvis du ikke har gjort oppgave 4 kan du her bruke toString()

    }


}
