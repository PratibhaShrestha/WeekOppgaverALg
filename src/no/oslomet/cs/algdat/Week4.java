package no.oslomet.cs.algdat;

import java.util.Arrays;

public class Week4 {

    public static void main(String[] args) {
        System.out.println("Uke4 algdat :D");
        int[] a = Week1.randomArray2(10);

        System.out.println("Array før partisjonering:");
        System.out.println(Arrays.toString(a));

        partition(a, 5, 0, a.length-1);

        System.out.println("Array etter partisjonering:");
        System.out.println(Arrays.toString(a));

        System.out.println("Array før quicksort:");
        System.out.println(Arrays.toString(a));
        quicksort(a, 0, a.length-1);
        System.out.println("Array etter quicksort:");
        System.out.println(Arrays.toString(a));


        {
            int k = fakultet(52);
            System.out.println("52! = " + k);
        }

        {
            int k = fakultet(1);
            System.out.println("1! = " + k);
        }

        {
            int k = fakultet(2);
            System.out.println("2! = " + k);
        }

        for (int i = 1; i<19; ++i) {
            int k = fakultet(i);
            System.out.println(i + "! = " + k);

        }

        int[] b = Week1.randomArray2(1000);

        System.out.println("Summen er " + sumIterative(b));
        System.out.println("Summen (rekursiv) er " + sumRecursive(b, a.length-1));



        System.out.println("2D array i minne");
        int[][] aa = {{1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}};
        int rows = aa.length;
        int cols = aa[0].length;
        for (int j=0; j<rows; ++j) {
            System.out.print("[" + aa[j][0]);
            for (int i=1; i<cols; ++i) {
                System.out.print(", " + aa[j][i]);
            }
            System.out.println("]");
        }

        System.out.println("2D array lineært i minne (ofte mer effektivt)");
        int[] aaa = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (int j=0; j<rows; ++j) {
            System.out.print("[" + aaa[j*cols]);
            for (int i=1; i<cols; ++i) {
                System.out.print(", " + aaa[j*cols+i]);
            }
            System.out.println("]");
        }
    }

    public static int sumRecursive(int[] a, int index) {
        if (index == 0) {
            return a[0];
        }
        else {
            return a[index] + sumRecursive(a, index-1);
        }
    }

    public static int sumIterative(int[] a) {
        int sum = a[0];
        for (int i=1; i<a.length; ++i) {
            sum += a[i];
        }
        return sum;
    }

    public static int fakultet(int n) {
        if (n == 1) {
            return 1;
        }
        else {
            return n * fakultet(n - 1);
        }
    }

    public static void bytt(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static int partition(int[] a, int pivot_index, int v, int h) {
        System.out.println("Partisjonerer med pivot = " + a[pivot_index]);

        //Bytt pivot-element eller skilleverdi til slutten
        bytt(a, pivot_index, h);
        pivot_index = h;


        // Dekrementer h for å ikke partisjonere skilleverdien
        // på slutten
        h = h-1;

        while (true) {
            //Flytt venstrepeker mot høyre
            //til vi har et ikke-pivotert tall
            while (v <= h && a[v] < a[pivot_index]) {
                v++;
            }
            //Flytt høyrepeker tilsvarende
            while (h >= v && a[h] >= a[pivot_index]) {
                h--;
            }

            //Hvis høyre er lik venstre
            //er alle tallene sortert
            if (v >= h) {
                bytt(a, pivot_index, v);
                System.out.println("Midten er på " + v + ", " + Arrays.toString(a));
                return v;
            }

            System.out.println("Vi bytter " + h + " med " + v);
            int tmp = a[v];
            a[v] = a[h];
            a[h] = tmp;
            v++;
            h--;
        }
    }

    public static void quicksort(int[] a, int v, int h) {
        if (v >= h) {
            return;
        }

        //1 velg en pivot f.eks. i midten
        int m = (v+h) / 2;

        //2 partisjoner tabellen og sorter tallet som ligger i
        //posisjon m
        int sortert_index = partition(a, m, v, h);

        //3 Kall rekursivt quicksort for høyre og venstre subtabeller
        //Venstre subtabell
        quicksort(a, v, sortert_index-1);
        quicksort(a, sortert_index+1, h);
    }


    public static int parter0(int[] a, int v, int h, int skilleverdi) {
        while (true) {                                 // stopper når v > h
            while (v <= h && a[v] < skilleverdi) {
                v++;   // h er stoppverdi for v
            }
            while (v <= h && a[h] >= skilleverdi) {
                h--;  // v er stoppverdi for h
            }

            if (v < h) {
                int tmp = a[v];
                a[v]= a[h];
                a[h] = tmp;
                v++;
                h--;
            }
            else  return v;  // a[v] er nåden første som ikke er mindre enn skilleverdi
        }
    }

}
