package sample.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.Model.ICategory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterController implements Initializable {
    private IController controller;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField revenusTextField;
    @FXML
    private ChoiceBox revenusChoiceBox;
    @FXML
    private TextField depensesTextField;
    @FXML
    private ChoiceBox depensesChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = Controller.getController();
        display();
    }

    public void display(){
        for(ICategory category : controller.getRevenuesCategory().getChildren()){
            revenusChoiceBox.getItems().add(category.getName());
        }
        revenusChoiceBox.getSelectionModel().selectFirst();
        for(ICategory category : controller.getDepensesCategory().getChildren()){
            depensesChoiceBox.getItems().add(category.getName());
        }
        depensesChoiceBox.getSelectionModel().selectFirst();
    }


    @FXML
    public void backWindow() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Scene.fxml"));
        rootPane.getChildren().setAll(pane);
    }

}
