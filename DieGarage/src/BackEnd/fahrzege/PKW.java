package BackEnd.fahrzege;

public class PKW extends Fahrzeug{

    @Override
    public String verlasseParkplatz() {
        getParkplatz().setFrei();
        parkAt(null);
        return "PKW | " + getNummernschild() + "verlaesst das Parkhaus";
    }

    @Override
    public String toString() {
        return "PKW | " + getNummernschild();
    }
}
