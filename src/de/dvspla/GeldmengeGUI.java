package de.dvspla;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GeldmengeGUI extends Geldmenge {

    private final TextField[] textFields;
    private final Label label;

    public GeldmengeGUI(HBox hBox) {
        super(0, 0, 0, 0, 0, 0, 0, 0);
        textFields = new TextField[money.length];

        Node[] nodes = hBox.getChildrenUnmodifiable().toArray(new Node[0]);
        for(int i = 0; i < textFields.length; i++) {
            textFields[i] = ((TextField) ((VBox) nodes[i]).getChildrenUnmodifiable().get(1));
        }

        this.label = ((Label) ((VBox) nodes[nodes.length - 1]).getChildrenUnmodifiable().get(0));
    }

    @Override
    public void setMoney(MoneyType moneyType, int anzahl) {
        try {
            super.setMoney(moneyType, anzahl);
            textFields[moneyType.ordinal()].setText(Integer.toString(anzahl));
        } catch (RuntimeException ex) {
            textFields[moneyType.ordinal()].setText("0");
        }
        int betrag = getBetrag();
        label.setText(betrag >= 100 ? String.format("%.2f%s", betrag / 100.0, "€") : betrag + "ct");
    }

    public void setMoney(String[] money) {
        for(int i = 0; i < money.length; i++) {
            setMoney(MoneyType.values()[i], Integer.parseInt(money[i]));
        }
    }

}
