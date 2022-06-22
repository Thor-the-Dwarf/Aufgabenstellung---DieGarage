package frontEnd;

import BackEnd.parkhaus.Parkhaus;
import BackEnd.parkhaus.parkplaetze.MotorradParkplatz;
import BackEnd.parkhaus.parkplaetze.PKWparkplatz;
import BackEnd.parkhaus.parkplaetze.Parkplatz;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Display extends JPanel {

    private ArrayList<JLabel>       labels;
    private Font                    font;
    private Parkhaus                parkhaus;

    public Display() {
        labels = new ArrayList<>();
        font = new Font("Verdana", Font.BOLD, 25);
        parkhaus = Parkhaus.getInstance();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addMessage("Willkommen");
    }

    /**
     * fuegt weitere standard benachrichtigungen hinzu
     */
    public void addMessage(String message) {
        JLabel temp = new JLabel(message, SwingConstants.CENTER); // ToDo 5: Centered Text functioniert nicht
        temp.setFont(font);
        this.add(temp);
    }

    /**
     * loescht aktuelle anzeige und zeigt rote Benachrichtigung
     */
    public void badMessage(String text) {
        clear();
        JLabel temp = new JLabel(text, SwingConstants.CENTER); // ToDo 5: Centered Text functioniert nicht
        temp.setFont(font);
        temp.setForeground(Color.RED);
        this.add(temp);
    }

    /**
     * loescht aktuelle anzeige und zeigt gruene Benachrichtigung
     */
    public void goodMessage(String text) {
        clear();
        JLabel temp = new JLabel(text, SwingConstants.CENTER); // ToDo 5: Centered Text functioniert nicht
        temp.setFont(font);
        temp.setForeground(new Color(0,180,0));
        this.add(temp);
    }

    /**
     * loescht aktuelle Anzeige
     */
    public void clear() {
        labels.clear();
        removeAll();
        revalidate();
        repaint();
    }
}
