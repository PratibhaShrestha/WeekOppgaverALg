package no.oslomet.oblig3;

import java.util.Comparator;
import java.util.TreeSet;

public class Oblig3 {


    public static void main(String[] args) {

        ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());

        System.out.println(tre.toString()); // [1, 2, 4, 4, 4, 6, 7, 7, 8, 9, 10]

    }


}
