package no.oslomet.cs.algdat;

import java.util.NoSuchElementException;
import java.util.Random;


public class Oppgave1_1_10 {
    public static void bytt(int[] a, int i, int j){
        int temp= a[i];
        a[i]= a[j];
        a[j]=temp;
    }
    public static int[] randPerm(int n){
        int[] a = new int[n];
        for(int i=0; i<n; i++)a[i]= i+1;
        Random r = new Random();
        for(int k=n-1; k>0;k--){
            int i = r.nextInt(k+1);
            bytt(a,k,i);
        }
        return a;
    }


    public static int maks1(int[] a) {
        if (a.length < 1)
            throw new java.util.NoSuchElementException("Tabellen er tom!");
        int m = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > a[m]) {
                m = i;
            }
        }
        return m;

    }

    public static int maks2(int[] a) {
        int m = 0;
        int maksverdi = a[0];
        for (int i = 0; i < a.length; i++) {
            if (a[i] > maksverdi) {
                m = i;
                maksverdi = a[i];
            }

        }
        return m;

    }

    public static int maks3(int[] a) {
        int sist = a.length - 1;
        int m = 0;
        int maksverdi = a[0];
        int temp = a[sist];
        a[sist] = 0x7fffffff;
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= maksverdi) {
                if (i == sist) {
                    a[sist] = temp;
                    return temp >= maksverdi ? sist : m;
                } else {
                    maksverdi = a[i];
                    m = i;
                }
            }
        }
        return m;
    }
    public static void main(String[] args) {
        int n=100_000,antall= 2_000;
        long tid=0;
        int a[]= randPerm(n);

        tid=System.currentTimeMillis();
        for(int i=0; i<antall; i++) maks1(a);
        tid=System.currentTimeMillis()-tid;
        System.out.println("Maks1-metoden:"+tid+"ms");

        tid=System.currentTimeMillis();
        for(int i=0; i<antall; i++) maks2(a);
        tid=System.currentTimeMillis()-tid;
        System.out.println("Maks2-metoden:"+tid+"ms");

        tid=System.currentTimeMillis();
        for(int i=0; i<antall; i++) maks3(a);
        tid=System.currentTimeMillis()-tid;
        System.out.println("Maks3-metoden:"+tid+"ms");



    }
}
