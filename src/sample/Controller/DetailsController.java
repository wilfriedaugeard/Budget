package sample.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableView<ICategory> revTable;
    @FXML
    private TableView<ICategory> depTable;
    @FXML
    private TableColumn<ICategory, String> listViewRev;
    @FXML
    private TableColumn<ICategory, String> listViewDep;
    @FXML
    private TableColumn<ICategory, String> listViewRevMontant;
    @FXML
    private TableColumn<ICategory, String> listViewDepMontant;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = Controller.getController();
        df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
        df.setMaximumFractionDigits(2);
        displayDetails();

    }

    public void putInList(HashMap<ICategory, Double> categoryList, ArrayList<ICategory> category, ArrayList<Montant> list){
        for(Montant m : list){
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

    public void addToListView(ArrayList<Montant> list, ArrayList<Montant> listRec,TableColumn<ICategory, String> label, TableColumn<ICategory, String> montant, TableView<ICategory> table){
        HashMap<ICategory, Double> categoryList = new HashMap<>();
        ArrayList<ICategory> category = new ArrayList<>();
        putInList(categoryList, category, listRec);
        putInList(categoryList, category, list);


        for(ICategory c : category){
            table.getItems().add(c);
            label.setCellValueFactory(param -> {
                final ICategory cat = param.getValue();
                return new SimpleStringProperty(cat.getName());
            });
            montant.setCellValueFactory(param -> {
                ICategory cat = param.getValue();
                String val = df.format(categoryList.get(cat))+" €";
                return new SimpleStringProperty(val);
            });
        }
    }

    public void displayDetails(){
        IPeriode month = controller.getGlobalPeriode().get(controller.getYearCursor()).getChild(controller.getMonthCursor());
        String winTitle = "Détails du mois de "+month.getName();
        title.setText(winTitle.toUpperCase());
        addToListView(month.getRevenues(), month.getRevenuesRec(),listViewRev, listViewRevMontant, revTable);
        addToListView(month.getDepenses(), month.getCharges(),listViewDep, listViewDepMontant, depTable);


    }

    @FXML
    public void backWindow() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Scene.fxml"));
        rootPane.getChildren().setAll(pane);
    }



}
