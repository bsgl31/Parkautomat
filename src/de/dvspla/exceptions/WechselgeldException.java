package de.dvspla.exceptions;

import de.dvspla.Geldmenge;
import de.dvspla.ParkautomatGUI;

public class WechselgeldException extends Exception {

    private final int amount;
    private final Geldmenge automatstand;

    public WechselgeldException(String message, int amount, Geldmenge automatstand) {
        super(message);
        this.amount = amount;
        this.automatstand = automatstand;
        ParkautomatGUI.log(message);
    }

    public int getAmount() {
        return amount;
    }

    public Geldmenge getAutomatstand() {
        return automatstand;
    }

}
