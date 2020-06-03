package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import sample.Factory.CategoryFactory;
import sample.Factory.PeriodeFactory;
import sample.Model.*;
import sample.View.View;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements IController {
    private View view;
    private int monthCursor;
    private int yearCursor;
    private int currentMonthCursor;
    private int currentYearCursor;
    private final ArrayList<IPeriode> globalPeriode;
    private ICategory revenuesCategory;
    private ICategory depensesCategory;
    private Displayer displayer;


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

    /* Constructor */
    public Controller () {
        globalPeriode = new ArrayList<>();
        dateTreatment();
        initializeModel();
    }

    @Override
    @FXML
    public void nextMonth(){
        monthCursor++;
        if(monthCursor == 12){
            monthCursor = 0;
            nextYear();
        }
        //setDisplayMonth();
    }

    @Override
    @FXML
    public void previousMonth(){
        monthCursor--;
        if(monthCursor < 0){
            monthCursor = 11;
            previousYear();
        }
        //setDisplayMonth();
    }

    public void nextYear(){
        yearCursor++;
        if(yearCursor >= globalPeriode.size())
            yearCursor = 0;
    }
    public void previousYear(){
        yearCursor--;
        if(yearCursor < 0)
            yearCursor = globalPeriode.size();
    }
/*
    public void setDisplayMonth(){
        String yearString = globalPeriode.get(yearCursor).getName();
        String monthString = globalPeriode.get(yearCursor).getChild(monthCursor).getName();
        String date = monthString +" "+yearString;
        dateLabel.setText(date);

        displayMonthRecap();
        displayCurrentmonth();
    }*/


    public void displayMonthRecap(){
        IPeriode month = globalPeriode.get(yearCursor).getChild(monthCursor);
        double revenues = month.getRevenues();
        double depenses = month.getDepenses();
        double epargnes = month.getEpargne();
        revenuesLabel.setText(Double.toString(revenues));
        depensesLabel.setText(Double.toString(depenses));
        epargnesLabel.setText(Double.toString(epargnes));
    }

    public void displayCurrentmonth(){
        IPeriode month = globalPeriode.get(currentYearCursor).getChild(currentMonthCursor);
        double revenues = month.getRevenues();
        double depenses = month.getDepenses();
        double epargnes = month.getEpargne();
        double budget = month.getBudget();
        currentRevenues.setText(Double.toString(revenues));
        currentDepenses.setText(Double.toString(depenses));
        currentEpargnes.setText(Double.toString(epargnes));
        currentBudget.setText(Double.toString(budget));
        epargnesTotal.setText(Double.toString(computeEpargneTotal()));
    }

    public double computeEpargneTotal(){
        double res = 0;
        for(IPeriode child : globalPeriode){
            res += child.getEpargne();
        }
        return res;
    }

    /*@Override
    public void initialize(URL location, ResourceBundle resources) {
        setDisplayMonth();
    }*/


    public void dateTreatment(){
        long millis=System.currentTimeMillis();
        String today = new java.sql.Date(millis).toString();
        String[] arrayDate = today.split("-");
        this.monthCursor = Integer.parseInt(arrayDate[1])-1;
        this.yearCursor = 0;
        this.currentMonthCursor = monthCursor;
        this.currentYearCursor = yearCursor;
    }

    /* Initialize methods */
    @Override
    public void initializeView() throws IOException {
        view = new View("Scene.fxml", 600, 400);
        FXMLLoader fxmlLoader = view.getFxmlLoader();
        displayer = fxmlLoader.getController();
        displayer.setController(this);
        displayer.setDisplayMonth();
    }

    public void initializeModel(){
        initializePeriode();
        initializeCategory();
    }

    public void initializeCategory(){
        List<String> revenuesList = Arrays.asList("Bourses","APL","Salaire","Salaire","Pension alimentaire","Autres");
        List<String> depensesList = Arrays.asList("Loyer","EDF","Eau","Abonnement/Forfait","Courses","Essences","Assurances","Restaurants/Bars","Autres");
        revenuesCategory = CategoryFactory.createGroupCategory("Revenues");
        depensesCategory = CategoryFactory.createGroupCategory("Depenses");
        for(String r : revenuesList){
            revenuesCategory.add(CategoryFactory.createCategory(r));
        }
        for (String d : depensesList){
            depensesCategory.add(CategoryFactory.createCategory(d));
        }
    }

    public void initializePeriode(){
        List<String> listMois = Arrays.asList("janvier","fevrier","mars","avril","mai","juin","juillet","aout","septembre","octobre","novembre","decembre");
        IPeriode annee2020 = PeriodeFactory.createYear("2020");
        IPeriode annee2021 = PeriodeFactory.createYear("2021");
        for(String mois : listMois){
            IPeriode m = PeriodeFactory.createMonth(mois);
            annee2020.addPeriode(m);
            annee2021.addPeriode(m);
        }
        globalPeriode.add(annee2020);
        globalPeriode.add(annee2021);
    }

    /* Getters */
    @Override
    public ArrayList<IPeriode> getGlobalPeriode() {
        return globalPeriode;
    }

    @Override
    public ICategory getRevenuesCategory() {
        return revenuesCategory;
    }

    @Override
    public ICategory getDepensesCategory() {
        return depensesCategory;
    }

    @Override
    public int getMonthCursor() {
        return monthCursor;
    }
    @Override
    public int getYearCursor() {
        return yearCursor;
    }
    @Override
    public int getCurrentMonthCursor() {
        return currentMonthCursor;
    }
    @Override
    public int getCurrentYearCursor() {
        return currentYearCursor;
    }

    public View getView() {
        return view;
    }

    @Override
    public Scene getScene(){
        return view.getScene();
    }
}
