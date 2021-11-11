package de.dvspla.exceptions;

import de.dvspla.ParkautomatGUI;

public class UnzureichendeGeldmengeException extends RuntimeException {

    private final int amount;

    public UnzureichendeGeldmengeException(String message, int amount) {
        super(message);
        this.amount = amount;
        ParkautomatGUI.log(message);
    }

    public int getAmount() {
        return amount;
    }

}
