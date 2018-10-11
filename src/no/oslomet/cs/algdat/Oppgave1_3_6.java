package no.oslomet.cs.algdat;

public class Oppgave1_3_6 {
    public static void main(String[] args) {
        int a[] = {1, 3, 4, 4, 5, 7, 7, 7, 7, 8, 9, 10, 10, 12, 15, 15, 15};

        System.out.println("Index of verdi is:" + binærsøk(a, 0, a.length - 1, 15));
    }

    public static int binærsøk(int[] a, int fra, int til, int verdi) {
        if (fra < 0 || til > a.length || fra == til) {
            throw new IllegalArgumentException("Illegal interval");
        }
        int v = fra;
        int h = til - 1;

        while (v < h) {
            int m = (v + h) / 2;
            int midtverdi = a[m];
            if (verdi > midtverdi) v = m + 1;
            else h = m;

        }
        if (h < v || verdi < a[v]) return -(v + 1);
        else if (verdi == a[v]) return v;
        else return -(v + 2);
    }
}
