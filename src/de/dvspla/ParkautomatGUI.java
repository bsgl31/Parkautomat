package de.dvspla;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParkautomatGUI extends Application {

    private static PrintWriter LOG;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public static void log(String msg) {
        LOG.println(FORMAT.format(new Date()) + " | " + msg);
        LOG.flush();
    }


    public static void main(String[] args) {
        try {
            LOG = new PrintWriter("fehler.log");
        } catch (FileNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Could not create log.").show();
        }
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ParkautomatGUIForm.fxml"));

        Scene scene = new Scene(root);

        stage.setResizable(false);
        stage.setTitle("Versand");
        stage.setScene(scene);
        stage.show();

        //stage.setOnCloseRequest(event -> );
    }
}
