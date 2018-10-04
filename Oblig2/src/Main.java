public class Main {

    public static void main(String[] args) {

        Integer[] i = {1,6,7,23,5,2};

        DobbeltLenketListe<Integer> dll = new DobbeltLenketListe<>(i);

        System.out.println(dll.antall());

        System.out.println();
        String[] s = {"Ole", null, "Per", "Kari", null};   Liste<String> liste = new DobbeltLenketListe<>(s);   System.out.println(liste.antall() + " " + liste.tom());

        // Utskrift: 3 false

    }
}
