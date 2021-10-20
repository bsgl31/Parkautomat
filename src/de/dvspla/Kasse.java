package de.dvspla;

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

        // Geht von hinten Geldtypen durch, 20€, 10€, 5€ etc.
        for (int i = moneyTypes.length - 1; i >= 0; i--) {
            MoneyType type = moneyTypes[i];

            // Wenn cent von aktuellem Typen kleiner als der Betrag, mach weiter
            // z.B. Betrag ist 9€, Typ ist 10€ Schein, kann gar nicht sein
            if (type.getCent() < cent) continue;

            // Solange die bezahlte Geldmenge Münzen von dem Typen hat
            while (tempBezahlt.getMoney(type) > 0) {
                // Entferne eine Münze von der bezahlten Geldmenge
                tempBezahlt.modifyMoney(type, -1);
                // Füge eine Münze zur Kopie der Geldmenge der Kasse hinzu
                tempMenge.modifyMoney(type, 1);
                // Entferne die Anzahl der cent die er bezahlen muss
                cent -= type.getCent();
            }
        }

        // Drehe Vorzeichen von cent um. Von "cent" wurde jetzt die Anzahl mit den größtmöglichen
        // Münzen die bezahlt wurden entfernt. Der übrige Minusbetrag ist das raus zugebende Wechselgeld
        // z.B. bezahlt = 5€ Schein, betrag = 2,60€. Von "cent" werden 5€ entfernt, da das der nächste
        //      mögliche Schein ist, der in bezahlt enthalten ist. Dadurch ist "cent" 2,40€ im Minus,
        //      da der Geldautomat jetzt dem anderen 2,40€ "schuldet" -> Vorzeichen umdrehen zum Weiterrechnen
        cent = -cent;

        // Gehe Typen von hinten durch, damit das Wechselgeld so groß wie möglich ist
        for (int i = moneyTypes.length - 1; i >= 0; i--) {
            MoneyType type = moneyTypes[i];

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

        // Wenn die Cent immer noch im Minus ist ist nicht genug Wechselgeld vorhanden
        if(cent < 0) throw new RuntimeException("Nicht genug Wechselgeld");

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
