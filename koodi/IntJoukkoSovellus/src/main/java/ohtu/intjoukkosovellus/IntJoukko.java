package ohtu.intjoukkosovellus;

import java.util.stream.IntStream;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] taulukko;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        taulukko = new int[KAPASITEETTI];
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("IntJoukon kapasiteetin tulee olla positiivinen luku.");
        }

        taulukko = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }
    
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("IntJoukon kapasiteetin tulee olla positiivinen luku.");
        } else if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("IntJoukon kasvatuskoon tulee olla positiivinen luku.");
        }

        taulukko = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    public boolean lisaa(int luku) {
        if (kuuluu(luku)) {
            return false;
        }

        taulukko[alkioidenLkm] = luku;
        alkioidenLkm++;
        kasvataTaulukonKokoa();
        return true;
    }

    public boolean kuuluu(int luku) {
        return IntStream.of(taulukko).anyMatch(x -> x == luku);
    }

    public boolean poista(int luku) {
        int indeksi = luvunIndeksi(luku);

        if (indeksi == -1) {
            return false;
        }

        taulukko[indeksi] = 0;
        jarjestaTaulukonLoppuUudelleen(indeksi);
        alkioidenLkm--;
        return true;
    }

    private void kasvataTaulukonKokoa() {
        // kasvata taulukon kokoa vain jos lisättiin yli maksimikoon
        if (alkioidenLkm < taulukko.length) {
            return;
        }
        
        int[] apu = new int[taulukko.length];
        kopioiTaulukko(taulukko, apu);
        taulukko = new int[alkioidenLkm + kasvatuskoko];
        kopioiTaulukko(apu, taulukko);
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
       for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }
    }

    private int luvunIndeksi(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == taulukko[i]) {
                return i;
            }
        }
        return -1;
    }

    private void jarjestaTaulukonLoppuUudelleen(int indeksi) {
        int apu;

        for (int i = indeksi; i < taulukko.length - 1; i++) {
            apu = taulukko[i];
            taulukko[i] = taulukko[i+1];
            taulukko[i+1] = apu;
        }
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
        String tuotos = "{";
        for (int i = 0; i < alkioidenLkm; i++) {
            tuotos += taulukko[i];

            if (i < alkioidenLkm - 1) {
                tuotos += ", ";
            }
        }
        return tuotos + "}";
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = taulukko[i];
        }
        return taulu;
    }
   

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            x.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            x.lisaa(bTaulu[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    y.lisaa(bTaulu[j]);
                }
            }
        }
        return y;

    }
    
    public static IntJoukko erotus (IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            z.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            z.poista(i);
        }
 
        return z;
    }
        
}