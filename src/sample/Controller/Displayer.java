package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sample.Model.IPeriode;
import sample.Model.Montant;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

public class Displayer implements Initializable {
    private IController controller;
    private DecimalFormat df;
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
    private Label chargesLabel;
    @FXML
    private Label epargnesLabel;
    @FXML
    private Label currentRevenues;
    @FXML
    private Label currentDepenses;
    @FXML
    private Label currentCharges;
    @FXML
    private Label currentEpargnes;
    @FXML
    private Label currentBudget;
    @FXML
    private Label epargnesTotal;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        controller = Controller.getController();
        df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
        df.setMaximumFractionDigits(2);
        setDisplayMonth();
    }


    public void setDisplayMonth(){
        String yearString = controller.getGlobalPeriode().get(controller.getYearCursor()).getName();
        String monthString = controller.getGlobalPeriode().get(controller.getYearCursor()).getChild(controller.getMonthCursor()).getName();
        String date = monthString +" "+yearString;
        dateLabel.setText(date.toUpperCase());

        displayMonthRecap();
        displayCurrentmonth();
    }

    public double computeCharges(IPeriode month){
        double charges = 0;
        for(Montant m : month.getCharges()){
            charges += m.getValue();
        }
        return charges;
    }

    public void displayMonthRecap(){
        IPeriode month = controller.getGlobalPeriode().get(controller.getYearCursor()).getChild(controller.getMonthCursor());
        double revenues = month.getRevenuesValue();
        double depenses = month.getDepensesValue();
        double epargnes = month.getEpargne();
        double charges = computeCharges(month);

        revenuesLabel.setText(df.format(revenues));
        chargesLabel.setText(df.format(charges));
        depensesLabel.setText(df.format(depenses));
        epargnesLabel.setText(df.format(epargnes));
    }

    public void displayCurrentmonth(){
        IPeriode month = controller.getGlobalPeriode().get(controller.getCurrentYearCursor()).getChild(controller.getCurrentMonthCursor());
        double revenues = month.getRevenuesValue();
        double depenses = month.getDepensesValue();
        double epargnes = month.getEpargne();
        double budget = month.getBudget();
        double charges = computeCharges(month);
        currentRevenues.setText(df.format(revenues));
        currentDepenses.setText(df.format(depenses));
        currentCharges.setText(df.format(charges));
        currentEpargnes.setText(df.format(epargnes));
        currentBudget.setText(df.format(budget));
        epargnesTotal.setText(df.format(controller.computeEpargneTotal()));
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

    @FXML
    void loadChargesWindow() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Charges.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    void loadDetailsWindow() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Details.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    void loadStatsWindow() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Stats.fxml"));
        rootPane.getChildren().setAll(pane);
    }


}
