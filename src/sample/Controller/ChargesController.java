package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.Model.Category;
import sample.Model.Montant;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChargesController implements Initializable {
    private IController controller;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField chargeToAddTF;
    @FXML
    private TextField montantTF;
    @FXML
    private ChoiceBox<String> choiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = Controller.getController();
        display();
    }

    public void display(){
        for (Montant montant : controller.getCurrentMonth().getCharges()){
            choiceBox.getItems().add(montant.getCategory().getName());
        }
    }



    @FXML
    public void ajouterButton(){
        String nameString = chargeToAddTF.getText();
        String montantString = montantTF.getText();
        if(!montantString.isEmpty() && !nameString.isEmpty()){
            if(!montantString.matches("(\\d+([,]|[.])\\d+)|\\d+")){
                montantTF.clear();
                return;
            }
            montantString = montantString.replace(",",".");

            double montant = Double.parseDouble(montantString);
            controller.addRecurrentCharge(new Montant(new Category(nameString),montant));
        }

    }

    @FXML
    public void supprimerButton(){
        if(!choiceBox.getSelectionModel().getSelectedItem().isEmpty()){
            controller.removeRecurrentCharge(choiceBox.getSelectionModel().getSelectedItem());
        }
    }


    @FXML
    public void backWindow() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Scene.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
