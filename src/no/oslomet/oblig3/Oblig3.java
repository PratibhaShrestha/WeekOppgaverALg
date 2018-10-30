package no.oslomet.oblig3;

import java.util.Comparator;
import java.util.TreeSet;

public class Oblig3 {


    public static void main(String[] args) {

        Integer[] a = {4, 7, 2, 9, 4, 10, 8, 7, 4, 6};
        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);

        System.out.println(tre.antall());
        System.out.println(tre.antall(5));
        System.out.println(tre.antall(4));
        System.out.println(tre.antall(7));
        System.out.println(tre.antall(10));

    }


}
