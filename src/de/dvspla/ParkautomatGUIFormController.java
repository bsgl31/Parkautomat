package de.dvspla;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ParkautomatGUIFormController implements Initializable {

    public HBox hboxBestand, hboxGezahlt, hboxRueckgeld, hboxButtons;
    public Label labelCurrentAmount;

    private GeldmengeGUI bestand, gezahlt, rueckgeld;

    private final HashMap<Button, MoneyType> buttonMoneyTypes = new HashMap<>();

    private int betragCent = 990;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bestand = new GeldmengeGUI(hboxBestand);
        gezahlt = new GeldmengeGUI(hboxGezahlt);
        rueckgeld = new GeldmengeGUI(hboxRueckgeld);

        Node[] buttons = hboxButtons.getChildrenUnmodifiable().toArray(new Node[0]);
        for (int i = 0; i < buttons.length; i++) {
            buttonMoneyTypes.put(((Button) buttons[i]), MoneyType.values()[i]);
        }

        try {
            Scanner scanner = new Scanner(new File("data/init.csv"));
            if(scanner.hasNextLine()) {
                bestand.setMoney(scanner.nextLine().trim().split(";"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onMoneyButtonClick(ActionEvent event) {
        for(Button button : buttonMoneyTypes.keySet()) {
            if(button.isDisable()) return;
            break;
        }

        gezahlt.modifyMoney(buttonMoneyTypes.get(event.getSource()), 1);
        if(gezahlt.getBetrag() >= betragCent) {
            Kasse kasse = new Kasse(bestand);
            try {
                Geldmenge wechselgeld = kasse.bezahle(betragCent, new Geldmenge(gezahlt));
                this.rueckgeld.setMoney(wechselgeld.getMoney());
            } catch (RuntimeException ex) {
                new Alert(Alert.AlertType.WARNING, "Kann nicht wechseln:\n" + ex.getMessage(), ButtonType.CLOSE).show();
            }
            for(Button button : buttonMoneyTypes.keySet()) {
                button.setDisable(true);
            }
        }
    }

    @FXML
    public void onRandomAmountButtonClick(ActionEvent event) {
        do {
            betragCent = (new Random().nextInt(2000));
        } while (betragCent < 1 || betragCent % 10 != 0);
        labelCurrentAmount.setText(betragCent >= 100 ? String.format("%.2f%s", betragCent / 100.0, "€") : betragCent + "ct");
        rueckgeld.setMoney(Geldmenge.EMPTY);
        gezahlt.setMoney(Geldmenge.EMPTY);
        for(Button button : buttonMoneyTypes.keySet()) {
            button.setDisable(false);
        }
    }

}
