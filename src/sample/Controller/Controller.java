package sample.Controller;

import sample.Factory.CategoryFactory;
import sample.Factory.PeriodeFactory;
import sample.Model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller implements IController{
    private ArrayList<IPeriode> globalPeriode;
    private ICategory revenuesCategory;
    private ICategory depensesCategory;

    /* Constructor */
    public Controller (){
        globalPeriode = new ArrayList<>();
        initializeModel();
    }

    /* Initialize methods */
    @Override
    public void initializeView() {

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
}
