package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import sample.Factory.CategoryFactory;
import sample.Factory.PeriodeFactory;
import sample.Model.*;
import sample.View.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
    /* Singleton */
    private Controller () {
        globalPeriode = new ArrayList<>();
        dateTreatment();
        initializeModel();
    }
    private static IController INSTANCE = null;

    public static IController getController(){
        if(INSTANCE == null)
            INSTANCE = new Controller();
        return INSTANCE;
    }

    @Override
    public void nextMonth(){
        monthCursor++;
        if(monthCursor == 12){
            monthCursor = 0;
            nextYear();
        }
    }

    @Override
    public void previousMonth(){
        monthCursor--;
        if(monthCursor < 0){
            monthCursor = 11;
            previousYear();
        }
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


    @Override
    public double computeEpargneTotal(){
        double res = 0;
        for(IPeriode child : globalPeriode){
            res += child.getEpargne();
        }
        return res;
    }


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
    }

    public void initializeModel(){
        initializePeriode();
        initializeCategory();
    }

    public void initializeCategory(){
        List<String> revenuesList = Arrays.asList("Bourses","APL","Salaire","Pension alimentaire","Autres");
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

    @Override
    public View getView() {
        return view;
    }

    @Override
    public Scene getScene(){
        return view.getScene();
    }
}
