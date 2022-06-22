package BackEnd.parkhaus.parkplaetze;

import BackEnd.fahrzege.Fahrzeug;
import BackEnd.fahrzege.Motorrad;


public class MotorradParkplatz extends Parkplatz {

    public MotorradParkplatz(int etagennummer, int reihennummer, int nummer) {
        super(etagennummer, reihennummer, nummer);
    }

    /**
     * prueft ob Caller (in diesem Fall PKW oder Motorrad) zu sich passt
     * */
    @Override
    public boolean checkFit(Fahrzeug fahrzeug) {
        return isFrei() & fahrzeug instanceof Motorrad;
    }
}
