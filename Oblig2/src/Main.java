public class Main {

    public static void main(String[] args) {

        //Integer[] i = {1,6,7,23,5,2};
        //DobbeltLenketListe<Integer> dll = new DobbeltLenketListe<>(i);
        //System.out.println(dll.omvendtString());

        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();

        System.out.println(liste.toString() + " " + liste.omvendtString());

        for (int i = 1; i <= 3; i++)   {     liste.leggInn(i);     System.out.println(liste.toString() + " " + liste.omvendtString());   }

        // Utskrift:   // [] []   // [1] [1]   // [1, 2] [2, 1]   // [1, 2, 3] [3, 2, 1

    }
}
