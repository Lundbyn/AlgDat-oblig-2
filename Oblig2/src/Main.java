public class Main {

    public static void main(String[] args) {

        Integer[] i = new Integer[500000];
        for (int j = 0; j < i.length; j++) {
            i[j] = 10+j;
        }

        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(i);
        System.out.println(liste.toString());
        liste.nullstill();
        System.out.println(liste.toString());

    }
}
