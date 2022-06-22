package BackEnd.parkhaus.parkplaetze;

import BackEnd.fahrzege.Fahrzeug;
import BackEnd.fahrzege.PKW;

public class PKWparkplatz extends Parkplatz {

    public PKWparkplatz(int etagennummer, int reihennummer, int nummer) {
        super(etagennummer, reihennummer, nummer);
    }

    /**
     * prueft ob Caller (in diesem Fall PKW oder Motorrad) zu sich passt
     * */
    @Override
    public boolean checkFit(Fahrzeug fahrzeug) {
        return isFrei() & fahrzeug instanceof PKW;
    }
}
