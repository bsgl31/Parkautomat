package de.dvspla;

public enum MoneyType {

    CENT_10(10, "10ct"),
    CENT_20(20, "20ct"),
    CENT_50(50, "50ct"),
    EURO_1(100, "1€"),
    EURO_2(200, "2€"),
    EURO_5(500, "5€"),
    EURO_10(1000, "10€"),
    EURO_20(2000, "20€");

    private final int cent;
    private final String prettyName;

    MoneyType(int cent, String prettyName) {
        this.cent = cent;
        this.prettyName = prettyName;
    }

    public int getCent() {
        return cent;
    }

    public String getPrettyName() {
        return prettyName;
    }
}
