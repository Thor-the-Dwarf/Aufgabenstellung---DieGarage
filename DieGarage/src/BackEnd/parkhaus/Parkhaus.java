package BackEnd.parkhaus;

import BackEnd.fahrzege.Fahrzeug;
import BackEnd.fahrzege.PKW;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Parkhaus implements Serializable {

    private ArrayList<Etage>        etagen;
    private int                     stockwerkCounter;

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<singleton>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private static Parkhaus parkhaus = new Parkhaus();

    private Parkhaus() {
        etagen =            new ArrayList<>();
        stockwerkCounter = -1;
    }

    public static Parkhaus getInstance() { return parkhaus; }
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<singleton>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    public void erzeugeEtage() { etagen.add(new Etage(++stockwerkCounter)); }

    public void loescheEtage(int etageNr) {
            etagen.remove(etageNr);
    }

    public String empfangeFahrzeug(Fahrzeug fahrzeug) {

        for (int i = 0; i < etagen.size(); i++) {                                       // iteriert durch Etagen
            for (int j = 0; j < etagen.get(i).getReihen().size(); j++) {                // iteriert durch Reihen
                for (int k = 0; k < etagen.get(i).getReihen().get(j).size(); k++) {     // iteriert durch Parkplätze

// im if-Statemant: parkplatz versucht Fahrzeug auf zu nehmen. Returniert boolean ob frei und Fahrzeugtyp geeignet ist
                    if(etagen.get(i).getReihen().get(j).get(k).setFahrzeug(fahrzeug)) {
                        return fahrzeug + " parkt auf " + etagen.get(i).getReihen().get(j).get(k).toString();
                    }
                }
            }
        }
        if(fahrzeug instanceof PKW) {
            return "kein Platz frei fuer " + fahrzeug;
        } else {
            return "kein Platz frei fuer " + fahrzeug;
        }
    }

    public ArrayList<Etage> getEtagen() {
        return etagen;
    }
}
