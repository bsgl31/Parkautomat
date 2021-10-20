package de.dvspla;

import java.util.Arrays;
import java.util.StringJoiner;

public class Geldmenge {

    private final int[] money;

    public Geldmenge(int cent10, int cent20, int cent50, int euro1, int euro2, int euro5, int euro10, int euro20) {
        money = new int[]{cent10, cent20, cent50, euro1, euro2, euro5, euro10, euro20};
    }

    public Geldmenge(int cent10, int cent20, int cent50, int euro1, int euro2) {
        this(cent10, cent20, cent50, euro1, euro2, 0, 0, 0);
    }

    public Geldmenge() {
        this(0, 0, 0, 0, 0, 0, 0, 0);
    }

    public Geldmenge(Geldmenge geldmenge) {
        this(geldmenge.money);
    }

    public Geldmenge(int[] money) {
        this.money = Arrays.copyOf(money, money.length);
    }


    public void setMoney(int[] money) {
        for(int i = 0; i < money.length; i++) {
            if(money[i] < 0) throw new RuntimeException("Money must be >= 0");
            this.money[i] = money[i];
        }
    }

    public void setMoney(MoneyType moneyType, int anzahl) {
        if(anzahl < 0) throw new RuntimeException("Money must be >= 0");
        money[moneyType.ordinal()] = anzahl;
    }

    public void modifyMoney(MoneyType moneyType, int anzahl) {
        setMoney(moneyType, getMoney(moneyType) + anzahl);
    }

    public int getMoney(MoneyType moneyType) {
        return money[moneyType.ordinal()];
    }

    public int[] getMoney() {
        return money;
    }

    public int getBetrag() {
        int cent = 0;
        for(MoneyType type : MoneyType.values()) {
            cent += money[type.ordinal()]*type.getCent();
        }
        return cent;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ");
        for(MoneyType type : MoneyType.values()) {
            if(getMoney(type) > 0) {
                joiner.add(getMoney(type) + "x" + type.getPrettyName());
            }
        }
        return "Geldmenge{betrag: " + getBetrag() + ", muenzen: " + joiner.toString() + "}";
    }

    public String toStringShort() {
        return Arrays.toString(money);
    }

}
