package sample.Controller;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.XmlOneway;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import sample.Model.ICategory;
import sample.Model.IPeriode;
import sample.Model.Montant;


import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class StatsController implements Initializable {
    private IController controller;
    private DecimalFormat df;
    private HashMap<ICategory, Double> categoryList;
    private ArrayList<ICategory> category;
    private ArrayList<ArrayList<Montant>> listRev;
    private ArrayList<ArrayList<Montant>> listDep;
    private double revValue;
    private double depValue;
    // View
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ChoiceBox<String> revChoice;
    @FXML
    private ChoiceBox<String> depChoice;
    @FXML
    private PieChart pieChartRev;
    @FXML
    private PieChart pieChartDep;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = Controller.getController();
        df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
        df.setMaximumFractionDigits(2);
        categoryList = new HashMap<>();
        category = new ArrayList<>();
        listRev = new ArrayList<>();
        listDep = new ArrayList<>();

        initializeChoiceBoxes();
        refreshPieChart();
    }

    public void initializeChoiceBoxes(){
        revChoice.getItems().addAll("Tout afficher","Afficher revenus","Afficher revenus récurrents");
        revChoice.getSelectionModel().selectFirst();
        revChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                refreshPieChart();
            }
        });
        depChoice.getItems().addAll("Tout afficher","Afficher dépenses","Afficher dépenses récurrentes");
        depChoice.getSelectionModel().selectFirst();
        depChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                refreshPieChart();
            }
        });
    }

    public void displayPieChart(PieChart pieChart, ArrayList<ArrayList<Montant>> lists, IPeriode month, String text, double value){
        categoryList.clear();
        category.clear();
        for(ArrayList<Montant> l : lists){
            listBrowse(l);
        }
        pieChart.setTitle(text+" du mois de "+month.getName());
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(ICategory c : category){
            pieChartData.add(new PieChart.Data(c.getName(), categoryList.get(c)*100/value));
        }
        pieChart.setData(pieChartData);
        pieChart.getData().forEach(data -> {
            String percentage = String.format("%.2f%%", (data.getPieValue()));
            Tooltip toolTip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), toolTip);
        });
    }

    public void listBrowse(ArrayList<Montant> listRec) {
        for(Montant m : listRec){
            if(categoryList.containsKey(m.getCategory())){
                double v = categoryList.get(m.getCategory());
                categoryList.put(m.getCategory(), v+m.getValue());
            }
            else{
                categoryList.put(m.getCategory(), m.getValue());
                category.add(m.getCategory());
            }
        }
    }

    public double choiceTreatment(int choice, ArrayList<ArrayList<Montant>> list, ArrayList<Montant> listMontant, ArrayList<Montant> listMontantRec){
        list.clear();
        double value = 0;
        switch (choice){
            case 0:
                list.add(listMontant);
                list.add(listMontantRec);
                for (Montant m : listMontant){
                    value += m.getValue();
                }
                for (Montant m : listMontantRec){
                    value += m.getValue();
                }
                break;
            case 2:
                list.add(listMontantRec);
                for (Montant m : listMontantRec){
                    value += m.getValue();
                }
                break;
            case 1:
                list.add(listMontant);
                for (Montant m : listMontant){
                    value += m.getValue();
                }
                break;
            default:
                break;
        }
        return value;
    }

    public void loadList(){
        IPeriode month = controller.getGlobalPeriode().get(controller.getYearCursor()).getChild(controller.getMonthCursor());
        int revChoiceIndex = revChoice.getSelectionModel().getSelectedIndex();
        int depChoiceIndex = depChoice.getSelectionModel().getSelectedIndex();
        revValue = choiceTreatment(revChoiceIndex, listRev, month.getRevenues(), month.getRevenuesRec());
        depValue = choiceTreatment(depChoiceIndex, listDep, month.getDepenses(), month.getCharges());
    }

    public void display(){
        IPeriode month = controller.getGlobalPeriode().get(controller.getYearCursor()).getChild(controller.getMonthCursor());
        displayPieChart(pieChartRev, listRev, month, "Revenus", revValue);
        displayPieChart(pieChartDep, listDep, month, "Dépenses", depValue);
    }


    public void refreshPieChart(){
        loadList();
        display();
    }

    @FXML
    public void backWindow() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Scene.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
