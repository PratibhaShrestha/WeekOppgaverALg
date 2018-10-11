package no.oslomet.cs.algdat;

import java.util.Random;

import static jdk.nashorn.internal.objects.NativeMath.max;

public class Oppgave1_2_2 {

    private Oppgave1_2_2(){}
    public static void bytt(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static int[] randPerm(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i + 1;
        Random r = new Random();
        for (int k = n - 1; k > 0; k--) {
            int i = r.nextInt(k + 1);
            bytt(a, k, i);
        }
        return a;
    }


    public static int maks(int[] a, int fra, int til) {
        if (fra < 0 || fra == til || til > a.length)
            throw new IllegalArgumentException("Illegal interval");
        int m = fra;
        int maksverdi = a[fra];
        for (int i = fra + 1; i < til; i++) {
            if (a[i] > maksverdi) {
                m = i;
                maksverdi = a[m];
            }
        }
        return m;

    }

    public static int maks(int[] a) {
        return max(a, 0, a.length);

    }


    public static void main(String[] args) {
        int[] a = Oppgave1_2_2.randPerm(20);
        for (int k : a) System.out.print(k + " ");
        int m = Oppgave1_2_2.maks(a);
        System.out.println("Største verdi ligger på plass" + m);


    }
}


