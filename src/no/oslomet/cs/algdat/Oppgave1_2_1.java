package no.oslomet.cs.algdat;

public class Oppgave1_2_1 {
    public static void main(String[] args) {
        int arr[] ={2,5,1,4,8,9};
        int minindeks = min(arr,0, arr.length);
        System.out.println("Minimum index is:"+minindeks);
        int minindex= min(arr);
        System.out.println("Minimum index #2:"+minindex);
        String names[]= {"Pratibha","Shrestha"};
        for(String s:names){
            System.out.print(s+" ");

        }

    }

    public static int min(int[] a, int fra, int til) {
        if (fra < 0 || til > a.length || fra >= til) {
            throw new IllegalArgumentException("Illegalt intervall");
        }
        int m = fra;
        int minverdi = a[fra];
        for(int i= fra+1; i< til; i++){
            if(a[i]< minverdi){
                m=i;
                minverdi = a[i];
            }
        }
        return m;

    }
    public static int min(int[] a){
        return min(a,0,a.length);
    }




    }

