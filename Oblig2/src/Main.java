public class Main {

    public static void main(String[] args) {

        Character[] c = {'A','B','D'};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);
        liste.leggInn(2,'C');
        System.out.println(liste.toString());
    }
}
