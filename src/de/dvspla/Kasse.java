package de.dvspla;

/**
 * Repräsentiert eine Kasse
 */
public class Kasse {

    private Geldmenge geldmenge;

    public Kasse(Geldmenge geldmenge) {
        this.geldmenge = geldmenge;
    }

    public Kasse(Kasse kasse) {
        this.geldmenge = new Geldmenge(kasse.geldmenge);
    }

    public Geldmenge bezahle(int cent, Geldmenge bezahlt) {
        if(cent % 10 != 0) throw new RuntimeException("Kein 10ct-Betrag");
        if (bezahlt.getBetrag() < cent) throw new RuntimeException("Nicht genug Geld");

        // kopiert aktuelle Geldmenge und angegebene Bezahlt Menge
        Geldmenge tempMenge = new Geldmenge(this.geldmenge);
        Geldmenge tempBezahlt = new Geldmenge(bezahlt);
        Geldmenge wechselgeld = new Geldmenge();

        MoneyType[] moneyTypes = MoneyType.values();

        // Zu zahlendes Wechselgeld (z.B. bezahlt: 5€, zu bezahlen: 2,60€ --> wechselgeld: 2,40€)
        cent = tempBezahlt.getBetrag() - cent;

        // Gehe Typen von hinten durch, damit das Wechselgeld so groß wie möglich ist
        for (int i = moneyTypes.length - 1; i >= 0; i--) {
            MoneyType type = moneyTypes[i];

            if(tempBezahlt.getMoney(type) > 0) {
                try {
                    tempMenge.modifyMoney(type, tempBezahlt.getMoney(type));
                    tempBezahlt.modifyMoney(type, -tempMenge.getMoney(type));
                } catch (RuntimeException ex) {
                    throw new RuntimeException("Nicht genug Wechselgeld");
                }
            }

            // Solange die aktuellen cent mit dem Typen gedeckt werden können
            // und die Menge des Scheins mindestens 1 ist
            while (cent >= type.getCent() && tempMenge.getMoney(type) > 0) {
                // Entferne die cent des aktuellen Typen
                cent -= type.getCent();
                // Füge den typen zum Wechselgeld hinzu und
                // entferne ihn von dem aktuellen Geldstand
                wechselgeld.modifyMoney(type, 1);
                tempMenge.modifyMoney(type, -1);
            }
        }

        // Wenn alles erfolgreich war, setze die Geldmenge der Kasse auf die neue
        // geldmenge + setze die Menge der bezahlten Geldmenge auf die neue Geldmenge
        this.geldmenge = tempMenge;
        bezahlt.setMoney(tempBezahlt.getMoney());
        return wechselgeld;
    }

    public Geldmenge getGeldmenge() {
        return geldmenge;
    }

    public int getBetrag() {
        return geldmenge.getBetrag();
    }

}
