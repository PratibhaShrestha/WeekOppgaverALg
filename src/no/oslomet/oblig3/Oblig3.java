package no.oslomet.oblig3;

import java.util.Comparator;
import java.util.TreeSet;

public class Oblig3 {


    public static void main(String[] args) {

        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());

        tre.leggInn(3);
        int[] a = {1, 8, 2, 4, 7, 5, 6, 6};
        for (int verdi : a) tre.leggInn(verdi);


    }
}
