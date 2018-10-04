import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

public class DobbeltLenketListe<T> implements Liste<T>
{
    private static final class Node<T>   // en indre nodeklasse
    {
        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }

    } // Node

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks)
    {
        int i = antall / 2;
        if (indeks < i) {
            Node<T> p = hode;
            for(int j = 0; j < indeks; j++) {
                p = p.neste;
            }
            return p;
        }
        else {
            Node<T> p = hale;
            for(int j = antall - 1; j > indeks; j--) {
                p = p.forrige;
            }
            return p;
        }
    }

    // konstruktør
    public DobbeltLenketListe()
    {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a)
    {
        //this();
        Objects.requireNonNull(a, "Tabellen a er null!");
        if (a.length == 0) return;
        int i = a.length - 1;
        for(; i > 0; i--) {
            if(a[i] != null) {
                break;
            }
        }
        if(a[i] == null) {
            return;
        }
        hode = hale = new Node<>(a[i]);
        antall++;
        Node<T> p = hale;

        for(i--; i >= 0; i--) {
            if(a[i] != null) {
                hode = new Node<T>(a[i], null, hode);
                p.forrige = hode;
                p = hode;
                antall++;
            }
        }
    }


    // subliste
    public Liste<T> subliste(int fra, int til)
    {

        fratilKontroll(antall, fra, til);

        Node<T> p = hode;
        for(int i = 0; i < fra; i++) {
            p = p.neste;
        }

        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>();

        for(int i = fra; i < til; i++) {
            liste.leggInn(p.verdi);
            p = p.neste;
        }

        return liste;
    }

    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi, "Verdien kan ikke være null!");

        if(tom()) {
            Node<T> p = new Node<>(verdi);
            hode = p;
            hale = p;
        }
        else {
            Node<T> p = new Node<>(verdi, hale, null);
            hale.neste = p;
            hale = p;
        }
        endringer++;
        antall++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi,"Verdien kan ikke være null");
        if(indeks < 0 || indeks > antall) {
            throw new IndexOutOfBoundsException("Ulovlig indeks");
        }

        if (tom()) {
            Node<T> p = new Node<>(verdi);
            hode = p;
            hale = p;
        }
        else {
            if(indeks == 0) {
                Node<T> p = new Node<>(verdi, null, hode);
                hode.forrige = p;
                hode = p;
            }
            else if(indeks == antall) {
                Node<T> p = new Node<>(verdi, hale, null);
                hale.neste = p;
                hale = p;
            }
            else {
                Node<T> p = hode;
                Node<T> q = hode;
                for(int i = 0; i < indeks; i++) {
                    p = q;
                    q = q.neste;
                }
                p.neste = new Node<>(verdi, p, q);
                q.forrige = p.neste;
            }
        }
        antall++;
        endringer++;
    }

    @Override
    public boolean inneholder(T verdi)
    {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks)
    {
        indeksKontroll(indeks,false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi)
    {
        if(verdi == null) return -1;

        Node<T> p = hode;
        for(int i = 0; i < antall; i++) {
            if(verdi.equals(p.verdi)) {
                return i;
            }
            p = p.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        indeksKontroll(indeks, false);
        Objects.requireNonNull(nyverdi, "Verdien kan ikke være null!");

        Node<T> p = finnNode(indeks);
        T gammelverdi = p.verdi;
        p.verdi = nyverdi;
        endringer++;
        return gammelverdi;
    }

    @Override
    public boolean fjern(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T fjern(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void nullstill()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append('[');

        if(!tom()) {

            Node<T> p = hode;
            s.append(p.verdi);
            p = p.neste;
            for (int i = 0; i < antall-1; i++) {
                s.append(", ");
                s.append(p.verdi);
                p = p.neste;
            }
        }

        s.append(']');
        return s.toString();
    }

    public String omvendtString()
    {
        StringBuilder s = new StringBuilder();
        s.append('[');

        if(!tom()) {

            Node<T> p = hale;
            s.append(p.verdi);
            p = p.forrige;
            for (int i = 0; i < antall-1; i++) {
                s.append(", ");
                s.append(p.verdi);
                p = p.forrige;
            }
        }

        s.append(']');
        return s.toString();
    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public Iterator<T> iterator()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public Iterator<T> iterator(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator()
        {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks)
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator


    private static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > Antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }
} // DobbeltLenketListe 