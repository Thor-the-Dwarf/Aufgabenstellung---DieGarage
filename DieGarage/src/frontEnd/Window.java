
package frontEnd;

import BackEnd.fahrzege.Motorrad;
import BackEnd.fahrzege.PKW;
import BackEnd.parkhaus.Parkhaus;
import BackEnd.parkhaus.parkplaetze.MotorradParkplatz;
import BackEnd.parkhaus.parkplaetze.PKWparkplatz;
import BackEnd.parkhaus.parkplaetze.Parkplatz;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

public class Window extends JFrame implements Serializable {

    {
        try {
            FileInputStream fileIn = new FileInputStream("saving of Parkhaus");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            parkhaus = (Parkhaus) objectIn.readObject();

            objectIn.close();
            fileIn.close();

        } catch (FileNotFoundException ignored) {
            // tritt nur bei der Ersten Sitzung ein oder wenn saving-Datei absichtlich geloescht wurde
            // wird ignoriert weil im Konstruktor erneut eine aehnliche Abfrage stattfindet
            // diese dient dazu das: Wenn der User keine Etagen erzeugt hat oder
            // allererste Sizung nicht gespeichert hat; Der User beim erneuten oeffnen
            // wieder in der allerersten Sitzung landet.
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Laden der Datei fehlgeschlagen");
            e.printStackTrace();
        }
    }

    private int                     aktuelleEtage;
    private GraphicsEnvironment     graphicsEnvironment;
    private GraphicsDevice          graphicsDevice;
    private Parkhaus                parkhaus;
    private JPanel                  base;
    private JPanel                  menuArea;
    private JPanel                  displayArea;
    private Display                 display;
    private Font                    buttonFont;
    private final JButton           WAEHLE_ETAGE;
    private final JButton           SAVE;

    public Window() {
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<initialize All Components>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        graphicsEnvironment =       GraphicsEnvironment.getLocalGraphicsEnvironment();  // macht fullscreen ToDo 1
        graphicsDevice =                    graphicsEnvironment.getDefaultScreenDevice();       // macht fullscreen ToDo 1

        base =                      new JPanel();                                       // Basis Anzeigentraeger
        menuArea =                  new JPanel();                                       // Menu-Elemente-Traeger
        displayArea =               new JPanel();                                       // Display-Elemente-Traeger
        display =                   new Display();                                      // User Anzeige
        buttonFont =                      new Font("Verdana", Font.BOLD, 20);
        WAEHLE_ETAGE =              new JButton("Etage " + aktuelleEtage);          // Button bekommt Name
        SAVE =                      new JButton("sicher schliessen");               // Button bekommt Name
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<initialize All Components ENDS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<combining Components>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        WAEHLE_ETAGE.addActionListener(e -> waehleEtage());                             // Button bekommt Comand
        SAVE.addActionListener(e -> save());                                            // Button bekommt Comand
        SAVE.setBackground(Color.RED);

        WAEHLE_ETAGE.setFont(buttonFont);                                                     // Button bekommt design
        SAVE.setFont(buttonFont);                                                             // Button bekommt design

        displayArea.add(display);                                                       // display bekommt Faehigkeit zur Anzeige
        base.setLayout(new GridLayout(1,2));                                  // Basis bekommt Layout
        base.add(displayArea);                                                          // Basis bekommt Anzeigebereich
        base.add(menuArea);                                                             // Basis bekommt Menubereich
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<combining Components ENDS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<window - setings>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                 // Programm Endet mit rotem X
        pack();                                                                         // Windowgroesse passt sich dem Inhalt an
        setResizable(false);                                                             // User kann Widowgroesse bestimmen
        setVisible(true);                                                               // Window wird sichtbar
        add(base);                                                                      // Window erhaelt alle Komponenten
        graphicsDevice.setFullScreenWindow(this);                                               // macht fullscreen ToDo 1
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<window - setings ENDS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<wenn vorherige Sitzung nicht geladen werden konnte>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        try {
            if(parkhaus.getEtagen().size() == 0) {      // stellt sicher das es auch die dann die "erste Sitzung ist" +
                throw new  NullPointerException();      // + auch wenn die letzte Sitzung ordnungsgemaess geschlossen wurde
            }
        } catch (NullPointerException ne) {

            parkhaus = Parkhaus.getInstance();

            display.clear();
            display.addMessage(" ");
            display.addMessage("Herzlich willkommen");
            display.addMessage("zur allerersten Sitzung");
            display.addMessage("in diesem Programm. ");
            display.addMessage(" ");
            display.addMessage("Bevor Sie den BusinessMode");
            display.addMessage("richtig benutzen koennen");
            display.addMessage("muessen Sie hier Etagen");
            display.addMessage("und Reihen erzeugen.");
            display.addMessage(" ");
            display.addMessage("in den Tests trat");
            display.addMessage("der Fehler auf, dass in");
            display.addMessage("diesem Moment auf der");
            display.addMessage("rechten Seite kein Menue");
            display.addMessage("zu sehen ist. Oder weniger");
            display.addMessage("als 7 Buttons angezeigt");
            display.addMessage("werden. Sollte das");
            display.addMessage("bei Ihnen der Fall sein.");
            display.addMessage("Stoppen Sie das Programm");
            display.addMessage("in der IDE oder im ");
            display.addMessage("Task-Manager. Und starten");
            display.addMessage("dieses Programm erneut.");
            display.addMessage("Sobald eine Etage erzeugt");
            display.addMessage("wurde tritt dieser Fehler");
            display.addMessage("nicht mehr auf");
            createMode();
            return;                     // verhindert das Construktor weiter ausgeführt wird und den busines startet
        }
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<wenn vorherige Sitzung nicht geladen werden konnte>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        businessMode();
    }

    /**
     * das komplete Inhalt des GUI wird neu aufgebaut -> "erzeugt Create - Mode"
     */
    private void createMode() {

        menuArea.removeAll();                                               // loesche vorheriges Menu
        menuArea.revalidate();                                              // loesche vorheriges Menu
        menuArea.repaint();                                                 // loesche vorheriges Menu

        ArrayList<JButton> buttons = new ArrayList<>();

        for (int i = 0; i < 5; i++) {                                       // erzeuge neue Buttons fuer das Menu
            buttons.add(new JButton());
            buttons.get(buttons.size()-1).setFont(buttonFont);
        }

        buttons.get(0).setText("erzeuge Etage");
        buttons.get(1).setText("loesche Etage");
        buttons.get(2).setText("erzeuge Reihe");
        buttons.get(3).setText("loesche Reihe");
        buttons.get(4).setText("buiseness Mode");



        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<erzeugeEtage - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        buttons.get(0).addActionListener(e -> {
            parkhaus.erzeugeEtage();                                 // BackEnd wird ausgefuehrt
            aktuelleEtage = parkhaus.getEtagen().size()-1;           // neu erzeugte Etage wird automatisch ausgewaehlt
            WAEHLE_ETAGE.setText("Etage " + aktuelleEtage);
            display.goodMessage("Etage " + aktuelleEtage + " wurde erzeugt"); // zeigt Benachrichtigung
        });
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<erzeugeEtage - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<loescheEtage - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
       buttons.get(1).addActionListener(e -> {
           String etage = null;
            try {
                if(aktuelleEtage == -1) {
                    throw new NullPointerException();
                }

                etage = JOptionPane.showInputDialog(this,                       // user input
                        "welche Etage soll geloescht werden?");

                parkhaus.loescheEtage(Integer.parseInt(etage));                              // BackEnd wird ausgeführt
                display.goodMessage("Etage " + etage                                    // zeigt Benachrichtigung
                        + " wurde geloescht");

            } catch (IndexOutOfBoundsException ie) {
                display.badMessage("Etage existiert nicht");
            } catch (NumberFormatException nfe) {
                if(etage == null) {
                    return;
                }
                display.badMessage("Bitte geben Sie eine Zahl ein");
            } catch (NullPointerException npe) {
                display.badMessage("es existieren noch keine Etagen");
            }
        });
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<loescheEtage - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<erzeugeReihe - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        buttons.get(2).addActionListener(e -> {

            String pkwPlaetze = null;
            String motorradPlaetze = null;
            int correctInput1 = 0;
            int correctInput2 = 0;

            try {
                pkwPlaetze = JOptionPane.showInputDialog(this,
                        "Wieviele PKW - Parkplaetze?");

                correctInput1 = Integer.parseInt(pkwPlaetze);


                motorradPlaetze = JOptionPane.showInputDialog(this,
                        "Wieviele Motorrad - Parkplaetze?");

                correctInput2 = Integer.parseInt(motorradPlaetze);

                parkhaus.getEtagen().get(aktuelleEtage).erzeugeReihe(correctInput1, correctInput2);

                display.goodMessage("Etage " + aktuelleEtage +
                        " Reihe " + parkhaus.getEtagen().get(aktuelleEtage).getReihen().size() +
                        " wurde erzeugt");

            } catch (IndexOutOfBoundsException ie) {
                display.badMessage("Sie müssen erst eine Etage erzeugen");
            } catch (NumberFormatException nfe) {
                if(pkwPlaetze == null | motorradPlaetze == null) { return; }
                display.badMessage("bitte geben Sie nur Zahlen ein");
            }
        });
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<erzeugeReihe - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<loescheReihe - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        buttons.get(3).addActionListener(e -> {

            String gewaehlteReihe = null;

            try {

                if(aktuelleEtage == -1) {
                    throw new NullPointerException();
                }

                gewaehlteReihe = JOptionPane.showInputDialog(this,
                        "welche Reihe soll geloescht werden?");

                int gewRei = Integer.parseInt(gewaehlteReihe);         // damit es eine (BuchstabenException nfe) geben kann

                parkhaus.getEtagen().get(gewRei).getReihen().         // BackEnd loescht Reihe
                        remove(gewRei);

                display.goodMessage("Reihe " + gewaehlteReihe + "wurde geloescht"); // ErfolgsAnzeige

            } catch (IndexOutOfBoundsException ie) {
                display.badMessage("gewaehlte Reihe existiert nicht");
            } catch (NumberFormatException ne) {
                if(gewaehlteReihe == null) {
                    return;
                }
                display.badMessage("bitte geben Sie nur Zahlen ein");
            } catch (NullPointerException npe) {
                display.badMessage("es existieren noch keine Etagen");
            }
        });
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<loescheReihe - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<businessMode - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        buttons.get(4).addActionListener(e -> businessMode());
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<businessMode - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        // Menu bekommt alle Komponenten
        menuArea.setLayout(new GridLayout(3,3));
        menuArea.add(buttons.get(0));
        menuArea.add(buttons.get(1));
        menuArea.add(WAEHLE_ETAGE);
        menuArea.add(buttons.get(2));
        menuArea.add(buttons.get(3));
        menuArea.add(new JLabel(" "));      // LeerLabel fuer die optic
        menuArea.add(SAVE);
        menuArea.add(new JLabel(" "));      // LeerLabel fuer die optic
        menuArea.add(buttons.get(4));
    }

    /**
     * das komplete Inhalt des GUI wird neu aufgebaut -> "erzeugt Buisenes - Mode"
     */
    private void businessMode() {

        menuArea.removeAll();                                                   // loesche vorheriges Menu
        menuArea.revalidate();                                                  // loesche vorheriges Menu
        menuArea.repaint();                                                     // loesche vorheriges Menu

        ArrayList<JButton> buttons = new ArrayList<>();

        for (int i = 0; i < 7; i++) {                                       // erzeuge neue Buttons fuer das Menu
            buttons.add(new JButton());
            buttons.get(buttons.size()-1).setFont(buttonFont);
        }

        buttons.get(0).setText("PKW kommt");
        buttons.get(1).setText("Motorrad kommt");
        buttons.get(2).setText("Fahrzeuge kommen");
        buttons.get(3).setText("Fahrzeuge verlassen uns");
        buttons.get(4).setText("liste Parkende auf");
        buttons.get(5).setText("finde Fahrzeug");
        buttons.get(6).setText("create Mode");

        buttons.get(6).addActionListener(e -> createMode());

        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<pkwKommt - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        buttons.get(0).addActionListener(e -> {
            display.clear();
            display.addMessage(parkhaus.empfangeFahrzeug(new PKW()));       // Frontend loest Backend-Methode aus
        });
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<pkwKommt - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<motorradKommt - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        buttons.get(1).addActionListener(e -> {
            display.clear();
            display.addMessage(parkhaus.empfangeFahrzeug(new Motorrad()));      // Frontend loest Backend-Methode aus
        });
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<motorradKommt - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<fahrzeugeKommen - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        buttons.get(2).addActionListener(e -> {

            String pkws = null;
            String motorraeder = null;
            int userInput1 = 0;
            int userInput2 = 0;
            try {
                pkws = JOptionPane.showInputDialog(this,      // user waehlt anzal ankommender pkws aus
                        "wie viele PKW`s kommen?");
                userInput1 = Integer.parseInt(pkws);

                motorraeder = JOptionPane.showInputDialog(this,// user waehlt anzal ankommender Motorraeder aus
                        "wie viele Motorraeder kommen?");

                userInput2 = Integer.parseInt(motorraeder);

                display.clear();                                                    // Anzeige wird geloescht
                for (int i = 0; i < userInput1; i++) {
                    display.addMessage(parkhaus.empfangeFahrzeug(
                            new PKW()));                                // Frontend loest Backend-Methode aus
                }
                for (int i = 0; i < userInput2; i++) {
                    display.addMessage(parkhaus.empfangeFahrzeug(
                            new Motorrad()));                           // Frontend loest Backend-Methode aus
                }
            } catch (NumberFormatException ne) {
                if(pkws == null & motorraeder == null) {
                    return;
                }
                display.badMessage("bitte geben Sie nur Zahlen ein");
            }
        });
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<fahrzeugeKommen - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Fahrzeuge verlassen uns - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        /**
         * User gibt userInput ein, das Parkhaus wird nach parkern durchsucht und so oft wie der userInput es angibt
         * werden Fahrzeuge entfernt. Oder es kommt zur Benachrichtigung wenn der User Mehr Fahrzeuge entlaesst als
         * im Parkhaus sind
         */
        buttons.get(3).addActionListener(e -> {

            String verlassenUns = null;

            try {
                verlassenUns = JOptionPane.showInputDialog(this,
                        "wie viele Fahrzeuge verlassen das Parkhaus?");

                int counter = Integer.parseInt(verlassenUns);
                int stopper = 2;                                                     // stopper verhinderd infinite Loop

                display.clear();
            outer:
                while (counter > 0 & stopper > 0) {                                  // biss alle Parker entfernt wurden
                    stopper--;
                    for (int i = 0; i < parkhaus.getEtagen().size(); i++) {                     // iteriert durch Etagen
                        for (int j = 0;                                                         // iteriert durch Reihen
                             j < parkhaus.getEtagen().get(i).getReihen().size(); j++) {
                                for (int k = 0;                                            // iteriert durch Parkplaetze
                                    k < parkhaus.getEtagen().get(i).getReihen().get(j).size(); k++) {

                                    if (!(parkhaus.getEtagen().get(i).getReihen().get(j).get(k).isFrei())) {
                                        display.addMessage(
                                                parkhaus.getEtagen().get(i).getReihen().get(j).get(k).getFahrzeug() +
                                                        " verlaesst das Parkhaus");

                                        parkhaus.getEtagen().get(i).getReihen().get(j).get(k).getFahrzeug().
                                                verlasseParkplatz();

                                                if(--counter == 0) {
                                                    break outer;
                                                }
                                    }
                                }
                        }
                    }
                }
                if (stopper == 0) {
                    display.addMessage(" ");
                    display.addMessage("Es haben mehr Fahrzeuge");
                    display.addMessage("das Parkhaus verlassen");
                    display.addMessage("als registriert wurden");
                }

            } catch (NumberFormatException ne) {
                if(verlassenUns == null) {
                } else {
                    display.badMessage("bitte geben Sie eine Zahl ein");
                }
            }
        });
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Fahrzeuge verlassen uns - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<liste Parkende auf - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        buttons.get(4).addActionListener(e -> {
            boolean parkhausFrei = true;
            display.clear();
            for (int i = 0; i < parkhaus.getEtagen().size(); i++) {
                for (int j = 0; j < parkhaus.getEtagen().get(i).getReihen().size(); j++) {
                    for (int k = 0; k < parkhaus.getEtagen().get(i).getReihen().get(j).size(); k++) {
                        if( ! parkhaus.getEtagen().get(i).getReihen().get(j).get(k).isFrei()) {
                            display.addMessage(
                                    parkhaus.getEtagen().get(i).getReihen().get(j).get(k).getFahrzeug().toString() +
                                        " steht auf " + parkhaus.getEtagen().get(i).getReihen().get(j).get(k).
                                            getFahrzeug().getParkplatz().toString());
                            parkhausFrei = false;
                        }
                    }
                }
            }
            if (parkhausFrei) {
                display.addMessage("Alle Parkplaetze sind frei");
            }
        });
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<liste Parkende auf - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<findeFahrzeug - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        buttons.get(5).addActionListener(e -> {
            try {
            String kennzeichen = JOptionPane.showInputDialog(this,   // UserInput gesuchtes Nummernschild
                        "welches Kennzeichen hat das gesuchte Fahrzeug?");
            if(kennzeichen == null | kennzeichen.equals("")) {
                return;
            }

                for (int i = 0; i < parkhaus.getEtagen().size(); i++) {
                    for (int j = 0; j < parkhaus.getEtagen().get(i).getReihen().size(); j++) {
                        for (int k = 0; k < parkhaus.getEtagen().get(i).getReihen().get(j).size(); k++) {

                                if(kennzeichen.equals(parkhaus.getEtagen().
                                        get(i).getReihen().get(j).get(k).getFahrzeug().getNummernschild())) {

                                    display.goodMessage(parkhaus.getEtagen().get(i).getReihen().get(j).get(k).
                                            getFahrzeug() + " parkt auf " +
                                            parkhaus.getEtagen().get(i).getReihen().get(j).get(k));
                                    return;
                                }
                        }
                    }
                }
                display.badMessage(kennzeichen + " nicht gefunden");

            } catch (NullPointerException ignored) {}            // Exception wird ignoriert
        });
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<findeFahrzeug - Button Command>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        menuArea.setLayout(new GridLayout(3,3));                    // fuege alle Komponenten ein
        menuArea.add(buttons.get(0));
        menuArea.add(buttons.get(1));
        menuArea.add(buttons.get(2));
        menuArea.add(buttons.get(3));
        menuArea.add(buttons.get(4));
        menuArea.add(buttons.get(5));
        menuArea.add(SAVE);
        menuArea.add(buttons.get(6));
        menuArea.add(WAEHLE_ETAGE);
    }

    /**
     * Button Command
     * */
    private void waehleEtage() {            // es wird ofter gebraucht daher ist es kein Lamda
        Integer gewaehlteEgtage = null;
        try {
            gewaehlteEgtage = Integer.parseInt(JOptionPane.showInputDialog(this,    // User waehlt Etage
                "waehlen Sie eine Etage",
                parkhaus.getEtagen().size()-1));

            parkhaus.getEtagen().get(gewaehlteEgtage);   // absichtliche Exception; verhindert das Button eine Etage zeigt die es nicht gibt
            aktuelleEtage = gewaehlteEgtage;

            ArrayList<ArrayList<Parkplatz>> reihen = parkhaus.getEtagen().get(aktuelleEtage).getReihen();

            if (reihen.size() == 0) {
                display.badMessage("Auf dieser Etage sind noch keine Parkplaetze");

                if(aktuelleEtage < parkhaus.getEtagen().size()) {
                    WAEHLE_ETAGE.setText("Etage " + aktuelleEtage);
                }
                return;         // weil es die Methode verlaesst. eine Art Exception handling
            }

            int pkwParkplaetze = 0;
            int motorradParkplaetze = 0;
            int freiePKWplaetze = 0;
            int freieMotorradPlatze = 0;

            for (int i = 0; i < reihen.size(); i++) {
                display.addMessage(" ");
                display.addMessage("Reihe " + (i+1));

                pkwParkplaetze = 0;
                motorradParkplaetze = 0;
                freiePKWplaetze = 0;
                freieMotorradPlatze = 0;

                for (int j = 0; j < reihen.get(i).size(); j++) {
                    if(reihen.get(i).get(j) instanceof PKWparkplatz) {
                        pkwParkplaetze++;

                        if(reihen.get(i).get(j).isFrei()) {
                            freiePKWplaetze++;
                        }
                    } else if (reihen.get(i).get(j) instanceof MotorradParkplatz) {
                        motorradParkplaetze++;

                        if(reihen.get(i).get(j).isFrei()) {
                            freieMotorradPlatze++;
                        }
                    }
                }
                WAEHLE_ETAGE.setText("Etage " + aktuelleEtage);
                display.clear();
                display.addMessage("Etage " + aktuelleEtage + " ist ausgewaehlt");
                display.addMessage("Gesamt: " + pkwParkplaetze + " PKW-Parkplaetze | "
                        + motorradParkplaetze + " Motorrad-Parkplaetze");
                display.addMessage("Frei: " + freiePKWplaetze + " PKW-Parkplaetze | " +
                        freieMotorradPlatze + " Motorrad-Parkplaetze");
            }

        } catch (IndexOutOfBoundsException e) {
            display.badMessage("dieses Parkdeck existiert nicht");                   // zeige MisserfolgsBenachrichtigung
        } catch (NumberFormatException ne) {
            if(gewaehlteEgtage == null) {  return; }
            display.badMessage("bitte geben Sie Sie eine Zahl ein");
        }

    }

    /**
     * Button Command
     * */
    private void save() {

        RunnableClose schliessen =          new RunnableClose();
        Thread thread =                     new Thread(schliessen);

        try {
            FileOutputStream fileOut =      new FileOutputStream("saving of Parkhaus");
            ObjectOutputStream objectOut =  new ObjectOutputStream(fileOut);

            objectOut.writeObject(parkhaus);

            objectOut.close();
            fileOut.close();
            thread.start();

        } catch (Throwable t) {
            display.badMessage("Speichern fehlgeschlagen");
        }

    }

    /**
     * blockiert die Faehigkeit zu minimieren.
     *
     * Ich habe die Methode ueberschrieben weil JOptionPane bei jedem Aufruf das Hauptfenster minimiert
     * */
    @Override
    protected void processWindowEvent(WindowEvent e) { return; }

    class RunnableClose implements Runnable {

        @Override
        public void run() {
            System.exit(0);
        }
    }
}