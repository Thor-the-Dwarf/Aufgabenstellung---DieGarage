package BackEnd.parkhaus;

import BackEnd.parkhaus.parkplaetze.MotorradParkplatz;
import BackEnd.parkhaus.parkplaetze.PKWparkplatz;
import BackEnd.parkhaus.parkplaetze.Parkplatz;

import java.io.Serializable;
import java.util.ArrayList;

public class Etage  implements Serializable {

    private ArrayList<ArrayList<Parkplatz>>     reihen;
    private final int STOCKWERK;  // wird fuer die ParkplatzNummer verwendet

    public Etage(int STOCKWERK) {
        this.reihen =           new ArrayList<>();
        this.STOCKWERK = STOCKWERK;
    }

    /** erzeugt Parkplatzreihe mit Motorad- und PKW- Parkplaetzen*/
    public void erzeugeReihe(int anzahlPKWparkplatz, int anzahlMotorradParkplatz) {

        this.reihen.add(new ArrayList<>());

        for (int i = 0; i < anzahlPKWparkplatz; i++) {
            reihen.get(reihen.size()-1).add(new PKWparkplatz(STOCKWERK, reihen.size(), reihen.get(reihen.size()-1).size()));
        }
        for (int i = 0; i < anzahlMotorradParkplatz; i++) {
            reihen.get(reihen.size()-1).add(new MotorradParkplatz(STOCKWERK, reihen.size(), reihen.get(reihen.size()-1).size()));
        }

    }



    public ArrayList<ArrayList<Parkplatz>> getReihen() {
        return reihen;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        if (reihen.size() == 0) {
            sb.append("Auf dieser Etage sind noch keine Parkflaechen vorhanden");
            return sb.toString();
        }

        int pkwParkplaetze = 0;
        int motorradParkplaetze = 0;

        for (int i = 0; i < reihen.size(); i++) {
            sb.append("Reihe " + i+1 + " | ");

            for (int j = 0; j < reihen.get(i).size(); j++) {
                if(reihen.get(i).get(j) instanceof PKWparkplatz) {
                    pkwParkplaetze++;
                } else if (reihen.get(i).get(j) instanceof MotorradParkplatz) {
                    motorradParkplaetze++;
                }
            }
            sb.append(pkwParkplaetze + " PKW-Parkplaetze | " + motorradParkplaetze + " Motorrad-Parkplatze");
        }
        return sb.toString();
    }
}
