package BackEnd.parkhaus.parkplaetze;

import BackEnd.fahrzege.Fahrzeug;

import java.io.Serializable;

public abstract class Parkplatz  implements Serializable {

    private int etagennummer;
    private int reihennummer;
    private int eigeneNr;
    private Fahrzeug fahrzeug;
    private boolean frei = true;

    public Parkplatz(int etagennummer, int reihennummer, int eigeneNr) {
        this.etagennummer = etagennummer;
        this.reihennummer = reihennummer;
        this.eigeneNr = eigeneNr;
    }

    public boolean isFrei() { return frei; }

    public Fahrzeug getFahrzeug() { return fahrzeug; }

    public void setFrei() {
        this.fahrzeug = null;
        this.frei = true;
    }

    public boolean setFahrzeug(Fahrzeug fahrzeug) {
        if(checkFit(fahrzeug)) {
            this.fahrzeug = fahrzeug;
            this.fahrzeug.parkAt(this);
            this.frei = false;
            return true;
        }
        return false;
    }

    @Override
    public String toString() { return "E"+etagennummer + " R"+ reihennummer + " N"+ eigeneNr;  }

    public abstract boolean checkFit(Fahrzeug fahrzeug);
}
