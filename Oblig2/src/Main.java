public class Main {

    public static void main(String[] args) {

        String[] c = new String[10000000];
        for(int i = 0; i < c.length; i++) {
            c[i] = "Jamann";
        }
        DobbeltLenketListe<String> liste = new DobbeltLenketListe<>(c);
        System.out.println(liste.toString());
        liste.nullstill();
        System.out.println(liste.toString());
    }
}
