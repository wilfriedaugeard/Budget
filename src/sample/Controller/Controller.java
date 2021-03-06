package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import sample.Factory.CategoryFactory;
import sample.Factory.PeriodeFactory;
import sample.Model.*;
import sample.View.View;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Controller implements IController, Serializable {
    private static final long serialVersionUID = 1L;
    private boolean existState;
    private transient View view;
    private int year;
    private int monthCursor;
    private int yearCursor;
    private int currentMonthCursor;
    private int currentYearCursor;
    private ArrayList<IPeriode> globalPeriode;
    private ICategory revenuesCategory;
    private ICategory depensesCategory;


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
        setState();
        if(existState){
            loadState(this);
        }
    }
    private static IController INSTANCE = null;

    public static IController getController(){
        if(INSTANCE == null)
            INSTANCE = new Controller();
        return INSTANCE;
    }

    /**
     * Save the state
     */
    @Override
    public void saveState() {
        existState = true;
        String filename = "state.ctrl";
        ObjectOutputStream oos = null;
        try {
            File file = new File(filename);
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(this);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setState(){
        String filename = "state.ctrl";
        ObjectInput ois = null;
        try{
            final FileInputStream file = new FileInputStream(filename);
            existState = true;
        } catch (IOException e){
            existState = false;
        }
    }

    public void loadState(IController controller){
        String filename = "state.ctrl";
        ObjectInput ois = null;
        try {
            final FileInputStream file = new FileInputStream(filename);
            ois = new ObjectInputStream(file);
            final Controller controller_load = (Controller) ois.readObject();
            controller.setGlobalPeriode(controller_load.getGlobalPeriode());
            controller.setDepensesCategory(controller_load.getDepensesCategory());
            controller.setRevenuesCategory(controller_load.getRevenuesCategory());

        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
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
        if(yearCursor >= globalPeriode.size()){
            int yearToCreate = Integer.parseInt(globalPeriode.get(globalPeriode.size()-1).getName());
            createYear(yearToCreate+1);
        }
    }
    public void previousYear(){
        yearCursor--;
        if(yearCursor < 0) {
            int yearToCreate = Integer.parseInt(globalPeriode.get(0).getName());
            createYear(yearToCreate-1);
            yearCursor = 0;
            currentYearCursor++;
        }
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
        this.year = Integer.parseInt(arrayDate[0]);
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
        List<String> revenuesList = Arrays.asList("Mouvement épargne","Autres");
        List<String> depensesList = Arrays.asList("Courses","Essences","Assurances","Restaurants/Bars","Mouvement épargne","Autres");
        revenuesCategory = CategoryFactory.createGroupCategory("Revenues");
        depensesCategory = CategoryFactory.createGroupCategory("Depenses");
        for(String r : revenuesList){
            revenuesCategory.add(CategoryFactory.createCategory(r));
        }
        for (String d : depensesList){
            depensesCategory.add(CategoryFactory.createCategory(d));
        }
    }

    public void createYear(int yearToCreate){
        List<String> listMois = Arrays.asList("janvier","février","mars","avril","mai","juin","juillet","aout","septembre","octobre","novembre","décembre");
        String anneeString = String.valueOf(yearToCreate);
        IPeriode annee = PeriodeFactory.createYear(anneeString);
        for(String mois : listMois){
            IPeriode m = PeriodeFactory.createMonth(mois);
            annee.addPeriode(m);
        }
        for(int i=0; i<globalPeriode.size(); i++){
            int yearToCompare = Integer.parseInt(globalPeriode.get(i).getName());
            if(yearToCompare > yearToCreate){
                globalPeriode.add(i, annee);
                return;
            }
        }
        globalPeriode.add(annee);
    }

    public void initializePeriode(){
        int i = 0;
        int y = year;
        while (i<2){
            createYear(y);
            i++;
            y++;
        }
    }

    public void addRecurrent(Montant montant, boolean isRevenu){
        int j = monthCursor;
        for(int i = yearCursor; i<globalPeriode.size(); i++){
            while(j<12){
                if(isRevenu){
                    globalPeriode.get(i).getChild(j).addRevenuesRec(montant);
                }else{
                    globalPeriode.get(i).getChild(j).addCharges(montant);
                }
                j++;
            }
            j=0;
        }
    }

    @Override
    public void removeRecurrent(String name, boolean isRevenu){
        Montant toDelete = null;
        if(isRevenu){
            for(Montant m : getCurrentMonth().getRevenuesRec()){
                if(m.getCategory().getName().equals(name)){
                    toDelete = m;
                }
            }
        }else{
            for(Montant m : getCurrentMonth().getCharges()){
                if(m.getCategory().getName().equals(name)){
                    toDelete = m;
                }
            }
        }

        if(toDelete != null){
            int j = currentMonthCursor;
            for(int i = currentYearCursor; i<globalPeriode.size(); i++){
                while(j<12){
                    if(isRevenu){
                        globalPeriode.get(i).getChild(j).removeRevenuRec(toDelete);
                    }else{
                        globalPeriode.get(i).getChild(j).removeCharges(toDelete);
                    }
                    j++;
                }
                j=0;
            }
        }
    }

    public IPeriode getCurrentMonth(){
        return globalPeriode.get(currentYearCursor).getChild(currentMonthCursor);
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

    /* Setters */
    @Override
    public void setRevenuesCategory(ICategory revenuesCategory) {
        this.revenuesCategory = revenuesCategory;
    }
    @Override
    public void setDepensesCategory(ICategory depensesCategory) {
        this.depensesCategory = depensesCategory;
    }
    @Override
    public void setGlobalPeriode(ArrayList<IPeriode> globalPeriode) {
        this.globalPeriode = globalPeriode;
    }
}
