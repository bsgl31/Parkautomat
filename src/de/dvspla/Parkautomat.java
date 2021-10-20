package de.dvspla;

public class Parkautomat {

    public static void main(String[] args) {
        // 5x10c 0x20c, 8x50c, 0x1€, 7x2€, keine Scheine
        Geldmenge startMenge = new Geldmenge(5, 0, 8, 0, 7, 0, 0, 0);
        System.out.println(startMenge);
        //Erzeugt eine Kasse mit dem obigen Startkapital
        Kasse kasse = new Kasse(startMenge); //
        System.out.println("kasse = " + kasse.getGeldmenge());

        System.out.println("11111111111111111111111111111111111111111111");

        // Zahlt den geforderten Betrag mit 1x5€
        // Der Kassenautomat gibt 2,40€ zurück(4,0,0,0,1,0,0,0)
        // Da keine 20erl vorhanden sind, werden stattdessen vier 10erl zurückgegeben.
        // Zum Geldspeicher der Kasse kommt ein 5€-Schein hinzu und vier 10erl sowie
        // ein 2€ Stücck weg.
        Geldmenge zahlung1 = new Geldmenge(0, 0, 0, 0, 0, 1, 0, 0);
        System.out.println("zahlung1 = " + zahlung1);
        Geldmenge rueck1 = kasse.bezahle(260, zahlung1);
        System.out.println("rueck1 = " + rueck1);

        System.out.println("kasse = " + kasse.getGeldmenge());

        System.out.println("2222222222222222222222222222222222222222222222222");
        // Zahlt den geforderten Betrag von 2,60€ mit 2x2€
        // Der Kassenautomat kann dieses Mal nicht anständig zurückgeben, weil
        // nicht genügend 20erl und 10erl vorhanden sind.
        // ==> Die Methode bezahle wirft eine Exception.
        // Der Inhalt des Münzspeichers, bleibt bei dieser fehlgeschlagenen Zahlung
        // unverändert.
        try {
            Geldmenge zahlung2 = new Geldmenge(0, 0, 0, 0, 2);  // Konstruktor nur für Münzen
            System.out.println("zahlung2 = " + zahlung2);
            Geldmenge rueck2 = kasse.bezahle(260, zahlung2); // ==> Exception
            System.out.println("rueck2 = " + rueck2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("333333333333333333333333333333333333333333333333");

        System.out.println("kasse = " + kasse.getGeldmenge());
        System.out.println("kasse = " + kasse.getGeldmenge().toStringShort());

        // Im Geldspeicher der Kasse sind nun (1,0,8,0,6,1,0,0)
    }

}
