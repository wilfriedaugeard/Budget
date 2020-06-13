package sample.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
    // View
    @FXML
    private AnchorPane rootPane;
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
        display();
    }

    public void displayPieChart(PieChart pieChart, ArrayList<Montant> list, ArrayList<Montant> listRec, IPeriode month, String text, double value){
        categoryList.clear();
        category.clear();
        listBrowse(list);
        listBrowse(listRec);
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

    public void display(){
        IPeriode month = controller.getGlobalPeriode().get(controller.getYearCursor()).getChild(controller.getMonthCursor());
        displayPieChart(pieChartRev, month.getRevenues(), month.getRevenuesRec(), month, "Revenus", month.getRevenuesValue());
        displayPieChart(pieChartDep, month.getDepenses(), month.getCharges(), month, "Dépenses", month.getRevenuesValue());
    }



    @FXML
    public void backWindow() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Scene.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
