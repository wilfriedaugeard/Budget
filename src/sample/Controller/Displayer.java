package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import sample.Model.IPeriode;
import java.net.URL;
import java.util.ResourceBundle;

public class Displayer implements Initializable {
    private IController controller;
    // VIEW
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

    public void setController(IController controller) {
        this.controller = controller;
    }
}
