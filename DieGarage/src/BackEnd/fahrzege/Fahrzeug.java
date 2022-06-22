package BackEnd.fahrzege;

import BackEnd.parkhaus.parkplaetze.Parkplatz;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
// 2
public abstract class Fahrzeug  implements Serializable {

    private static HashSet<String> einzigartigeNummernschilder = new HashSet<>();

    private String nummernschild;
    private Parkplatz parkplatz = null;

    protected Fahrzeug() { this.nummernschild = einzigartigesNummernschild(); }

    public void parkAt(Parkplatz parkplatz) {
        this.parkplatz = parkplatz;
    }

    public String getNummernschild() { return nummernschild; }

    public Parkplatz getParkplatz() {
        return parkplatz;
    }

    private String einzigartigesNummernschild() {

        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();

        int bereich1 = rnd.nextInt(3) + 1;                                     // erzeuge 3 Nummernschildbereiche
        int bereich2 = rnd.nextInt(2) + 1;
        int bereich3 = 8 - bereich1 - bereich2;

        for (int i = 0; i < bereich1; i++) {                                    // fuellt bereich 1 mit RandomBuchstaben
            sb.append((char) (rnd.nextInt(25) + 65));
        }

        sb.append("-");

        for (int i = 0; i < bereich2; i++) {                                    // fuellt bereich 2 mit RandomBuchstaben
            sb.append((char) (rnd.nextInt(25) + 65));
        }

        sb.append("-");

        for (int i = 0; i < bereich3; i++) {                                        // fuellt bereich 3 mit RandomZahlen
            sb.append((char) (rnd.nextInt(10) + 48));
        }

        for (int i = 0; i < einzigartigeNummernschilder.size(); i++) {  // checkt ob erzeugtes Nummernschild bereits vorhanden ist
            if( ! (einzigartigeNummernschilder.add(sb.toString()))) {
                einzigartigesNummernschild();
            }
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return "Fahrzeug | " + nummernschild;
    }

    public abstract String verlasseParkplatz();

}
