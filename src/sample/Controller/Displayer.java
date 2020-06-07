package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Model.IPeriode;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Displayer implements Initializable {
    private IController controller;
    // VIEW
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label dateLabel;
    @FXML
    private Label revenuesLabel;
    @FXML
    private Label depensesLabel;
    @FXML
    private Label epargnesLabel;
    @FXML
    private Label currentRevenues;
    @FXML
    private Label currentDepenses;
    @FXML
    private Label currentEpargnes;
    @FXML
    private Label currentBudget;
    @FXML
    private Label epargnesTotal;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        controller = Controller.getController();
        setDisplayMonth();
    }


    public void setDisplayMonth(){
        String yearString = controller.getGlobalPeriode().get(controller.getYearCursor()).getName();
        String monthString = controller.getGlobalPeriode().get(controller.getYearCursor()).getChild(controller.getMonthCursor()).getName();
        String date = monthString +" "+yearString;
        dateLabel.setText(date);

        displayMonthRecap();
        displayCurrentmonth();
    }


    public void displayMonthRecap(){
        IPeriode month = controller.getGlobalPeriode().get(controller.getYearCursor()).getChild(controller.getMonthCursor());
        double revenues = month.getRevenues();
        double depenses = month.getDepenses();
        double epargnes = month.getEpargne();
        revenuesLabel.setText(Double.toString(revenues));
        depensesLabel.setText(Double.toString(depenses));
        epargnesLabel.setText(Double.toString(epargnes));
    }

    public void displayCurrentmonth(){
        IPeriode month = controller.getGlobalPeriode().get(controller.getCurrentYearCursor()).getChild(controller.getCurrentMonthCursor());
        double revenues = month.getRevenues();
        double depenses = month.getDepenses();
        double epargnes = month.getEpargne();
        double budget = month.getBudget();
        currentRevenues.setText(Double.toString(revenues));
        currentDepenses.setText(Double.toString(depenses));
        currentEpargnes.setText(Double.toString(epargnes));
        currentBudget.setText(Double.toString(budget));
        epargnesTotal.setText(Double.toString(controller.computeEpargneTotal()));
    }

    @FXML
    public void nextMonth(){
        controller.nextMonth();
        setDisplayMonth();
    }

    @FXML
    public void previousMonth(){
        controller.previousMonth();
        setDisplayMonth();
    }

    @FXML
    public void loadAjouterWindow() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Ajouter.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML void loadChargesWindow() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Charges.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
