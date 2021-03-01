/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minuterie;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author RSoloN
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ComboBox<Integer> comboHeures;
    @FXML
    private ComboBox<Integer> comboMinutes;
    @FXML
    private ComboBox<Integer> comboSecondes;

    Map<Integer, String> nombreMap;
    @FXML
    private TextField afficheMinuterie1;
    @FXML
    private TextField afficheMinuterie2;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ObservableList<Integer> listeHeures = FXCollections.observableArrayList();
        ObservableList<Integer> listeMinutes = FXCollections.observableArrayList();
        ObservableList<Integer> listeSecondes = FXCollections.observableArrayList();
        for (int i = 0; i < 60; i++) {
            if (i >= 0 && i < 24) {
                listeHeures.add(i);
            }
            listeMinutes.add(i);
            listeSecondes.add(i);
        }
        comboHeures.setItems(listeHeures);
        comboHeures.setValue(0);

        comboMinutes.setItems(listeMinutes);
        comboMinutes.setValue(0);

        comboSecondes.setItems(listeSecondes);
        comboSecondes.setValue(0);

        nombreMap = new TreeMap<Integer, String>();
        for (Integer i = 0; i < 60; i++) {
            if (i >= 0 && i <= 9) {
                nombreMap.put(i, "0" + i.toString());
            } else {
                nombreMap.put(i, i.toString());
            }
        }
    }
    private long min, sec, hr, totalSec = 0;

    long hmsVersSec() {
        totalSec = ((comboHeures.getValue() * 3600) + (comboMinutes.getValue() * 60) + comboSecondes.getValue());
        return totalSec;
    }

    private String format(long valeur) {
        if (valeur < 10) {
            return 0 + "" + valeur;
        }
        return valeur + "";
    }

    public void conversionTemps() {
        min = TimeUnit.SECONDS.toMinutes(totalSec);
        sec = totalSec - (min * 60);
        hr = TimeUnit.MINUTES.toHours(min);
        min = min - (hr * 60);
        afficheMinuterie2.setText(format(hr) + ":" + format(min) + ":" + format(sec));
        totalSec--;
    }

    @FXML
    private void afficher(ActionEvent event) {

        String heures = "";
        String minutes = "";
        String secondes = "";

        if (comboHeures.getValue() < 10) {
            heures = "0" + comboHeures.getValue().toString();
        } else {
            heures = comboHeures.getValue().toString();
        }

        if (comboMinutes.getValue() < 10) {
            minutes = "0" + comboMinutes.getValue().toString();
        } else {
            minutes = comboMinutes.getValue().toString();
        }
        if (comboSecondes.getValue() < 10) {
            secondes = "0" + comboSecondes.getValue().toString();
        } else {
            secondes = comboSecondes.getValue().toString();
        }
        afficheMinuterie1.setText(heures + ":" + minutes + ":" + secondes);
    }

    @FXML
    private void lancer(ActionEvent event) {
        hmsVersSec();
        Timer temps = new Timer();
        TimerTask tacheMinuterie = new TimerTask() {
            @Override
            public void run() {
                System.out.println("After 1 sec...");
                conversionTemps();
                if (totalSec == -1) {
                    temps.cancel();
                    afficheMinuterie2.setText("00:00:00");
                }
            }
        };
        temps.schedule(tacheMinuterie, 0, 1000);
    }

    @FXML
    private void stop(ActionEvent event) {
    }

}
