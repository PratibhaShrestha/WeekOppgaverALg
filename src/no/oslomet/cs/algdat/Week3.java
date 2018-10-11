package no.oslomet.cs.algdat;

import java.util.Arrays;

public class Week3 {

    public static void main(String[] args) {
        System.out.println("Hello algdat");

        int n = 12;
        int[] a = Week1.randomArray2(n);

        System.out.println("=====================================");
        System.out.println("Bubble sort her:");
        bubbleSort(a);

        System.out.println("=====================================");
        System.out.println("Finner maksimum i array");
        System.out.println("Initielt array:");
        System.out.println(Arrays.toString(a));
        int maks_indeks = findMax(a, 0, a.length);
        System.out.println("Maks er i " + maks_indeks);

        System.out.println("=====================================");
        System.out.println("Kjører selection sort");
        int[] b = Week1.randomArray2(n);
        for (int i=0; i<b.length; ++i) {
            b[i] = b[i]*10 + (int) (Math.random()*10);
        }
        System.out.println("Initielt array:");
        System.out.println(Arrays.toString(b));
        selectionSort(b);
        System.out.println("Array etter sortering:");
        System.out.println(Arrays.toString(b));

        System.out.println("=====================================");
        System.out.println("Binary search etter tall i listen!");
        binarySearch(b, b[n/2], 0, a.length-1);

        System.out.println("=====================================");
        System.out.println("Binary search etter tilfeldig tall!");
        binarySearch(b, 8, 0, a.length-1);
    }


    public static void binarySearch(int[] a, int k, int l, int r) {

        int m = (l + r)/2;

        System.out.println("Binærsøk etter " + k + " i " + Arrays.toString(a) + ", l=" + l + ", r=" + r + ", m=" + m);

        if (a[m] == k) {
            System.out.println("Fant " + k + " i posisjon " + m);
            return;
        }

        if ((r-l) <= 1) {
            System.out.println("Fant ikke tallet!");
            return;
        }

        if (a[m] < k) {
            System.out.println("Søker videre i intervallet [" + (m+1) + ", " + r + "]");
            binarySearch(a, k, m+1, r);
        }
        else if (a[m] > k) {
            System.out.println("Søker videre i intervallet [" + l + ", " + (m-1) + "]");
            binarySearch(a, k, l, m-1);
        }
    }

    public static void selectionSort(int[] a) {
        for (int i=0; i<a.length-1; ++i) {
            for (int k=i; k<a.length; ++k) {
                System.out.print(a[k] + ", ");
            }
            System.out.println();

            int max_index = findMin(a, i, a.length);
            int temp = a[max_index];
            a[max_index] = a[i];
            a[i] = temp;

            System.out.println("Bytter posisjon " + i + " og " + max_index);
            System.out.println(Arrays.toString(a));
        }
    }

    public static int findMax(int[] a, int fra, int til) {
        int current_max = a[fra];
        int current_index = fra;

        for (int i=fra+1; i<til; ++i) {
            if (a[i] > current_max) {
                current_max = a[i];
                current_index = i;
            }
        }

        return current_index;
    }


    public static int findMin(int[] a, int fra, int til) {
        int current_max = a[fra];
        int current_index = fra;

        for (int i=fra+1; i<til; ++i) {
            if (a[i] < current_max) {
                current_max = a[i];
                current_index = i;
            }
        }

        return current_index;
    }

    public static void bubbleSort(int[] a) {
        for (int i=0; i<a.length; ++i) {
            System.out.println("i=" + i);
            bubble(a);
        }
    }

    public static void bubble(int[] a) {
        // Loop over alle elementene i a
        System.out.println(Arrays.toString(a));
        for (int i=0; i<a.length-1; ++i) {
            if (a[i] > a[i+1]) {
                System.out.println("Inversjon i plass " + i + ", bytter om");
                int tmp = a[i];
                a[i] = a[i+1];
                a[i+1] = tmp;
                System.out.println(Arrays.toString(a));
            }
        }
    }
}
