package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import sample.Model.ICategory;
import sample.Model.IPeriode;
import sample.Model.Montant;


import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class DetailsController implements Initializable {
    private static final int LIST_CELL_HEIGHT = 26;
    private IController controller;
    private DecimalFormat df;

    // VIEW
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label title;
    @FXML
    private ListView<String> listViewRev;
    @FXML
    private ListView<String> listViewDep;
    @FXML
    private ListView<String> listViewRevMontant;
    @FXML
    private ListView<String> listViewDepMontant;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = Controller.getController();
        df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
        df.setMaximumFractionDigits(2);
        displayDetails();

    }

    public void addToListView(ArrayList<Montant> list, ListView<String> label, ListView<String> montant){
        HashMap<ICategory, Double> categoryList = new HashMap<>();
        ArrayList<ICategory> category = new ArrayList<>();
        for(Montant m : list){
            if(categoryList.containsKey(m.getCategory())){
                double value = categoryList.get(m.getCategory());
                categoryList.put(m.getCategory(), value+m.getValue());
            }
            else{
                categoryList.put(m.getCategory(), m.getValue());
                category.add(m.getCategory());
            }
        }
        for(ICategory c : category){
            label.getItems().add(c.getName());
            montant.getItems().add(df.format(categoryList.get(c))+" €");
        }
    }

    public void displayDetails(){
        IPeriode month = controller.getGlobalPeriode().get(controller.getYearCursor()).getChild(controller.getMonthCursor());
        String winTitle = "Détails du mois de "+month.getName();
        title.setText(winTitle.toUpperCase());
        addToListView(month.getRevenuesRec(),listViewRev, listViewRevMontant);
        addToListView(month.getRevenues(),listViewRev, listViewRevMontant);
        if(listViewRev.getItems().size() == 0){
            listViewRev.getItems().add("Aucun revenu pour "+month.getName());
            listViewRevMontant.setVisible(false);
            listViewRev.setMaxHeight(LIST_CELL_HEIGHT);
        }
        addToListView(month.getCharges(),listViewDep, listViewDepMontant);
        addToListView(month.getDepenses(),listViewDep, listViewDepMontant);
        if(listViewDep.getItems().size() == 0){
            listViewDep.getItems().add("Aucune dépense pour "+month.getName());
            listViewDepMontant.setVisible(false);
            listViewDep.setMaxHeight(LIST_CELL_HEIGHT);
        }

    }

    @FXML
    public void backWindow() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Scene.fxml"));
        rootPane.getChildren().setAll(pane);
    }



}
