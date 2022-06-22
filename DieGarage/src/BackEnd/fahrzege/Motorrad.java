package BackEnd.fahrzege;

public class Motorrad extends Fahrzeug {

    @Override
    public String verlasseParkplatz() {
        getParkplatz().setFrei();
        parkAt(null);
        return "Motorrad | " + getNummernschild() + "verlaesst das Parkhaus";
    }

    @Override
    public String toString() {
        return "Motorrad | " + getNummernschild();
    }
}
