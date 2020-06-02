package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class Controller implements IController, Initializable {
    private View view;
    private int date;;
    private ArrayList<IPeriode> globalPeriode;
    private ICategory revenuesCategory;
    private ICategory depensesCategory;


    // VIEW
    @FXML
    private Label dateLabel;

    /* Constructor */
    public Controller () {
        globalPeriode = new ArrayList<>();
        dateTreatment();
        initializeModel();
    }

    @Override
    @FXML
    public void nextMonth(){
        date++;
        setDisplayMonth();
    }

    @Override
    @FXML
    public void previousMonth(){
        date--;
        setDisplayMonth();
    }

    public void setDisplayMonth(){
        String year = globalPeriode.get(0).getName();
        if(date == 0)
            date = 12;
        if(date == 13)
            date = 1;
        String month = globalPeriode.get(0).getChildren().get(date-1).getName();
        String date = month +" "+year;
        dateLabel.setText(date);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDisplayMonth();
    }


    public void dateTreatment(){
        long millis=System.currentTimeMillis();
        String today = new java.sql.Date(millis).toString();
        String[] arrayDate = today.split("-");
        this.date = Integer.parseInt(arrayDate[1]);
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

    public ArrayList<IPeriode> getGlobalPeriode() {
        return globalPeriode;
    }

    public ICategory getRevenuesCategory() {
        return revenuesCategory;
    }

    public ICategory getDepensesCategory() {
        return depensesCategory;
    }

    public View getView() {
        return view;
    }

    @Override
    public Scene getScene(){
        return view.getScene();
    }
}
