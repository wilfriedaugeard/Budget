package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
    private ChoiceBox<String> choiceBoxCharge;
    @FXML
    private Label chargeAddName;
    @FXML
    private Label montantAddLabel;
    @FXML
    private Label chargeDelName;

    @FXML
    private TextField revenuToAddTF;
    @FXML
    private TextField montantRTF;
    @FXML
    private ChoiceBox<String> choiceBoxRevenu;
    @FXML
    private Label revenuAddName;
    @FXML
    private Label montantRAddLabel;
    @FXML
    private Label revenuDelName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = Controller.getController();
        display();
    }

    public void display(){
        for (Montant montant : controller.getCurrentMonth().getCharges()){
            choiceBoxCharge.getItems().add(montant.getCategory().getName());
        }
        choiceBoxCharge.getSelectionModel().selectFirst();
        for (Montant montant : controller.getCurrentMonth().getRevenuesRec()){
            choiceBoxRevenu.getItems().add(montant.getCategory().getName());
        }
        choiceBoxRevenu.getSelectionModel().selectFirst();


    }



    @FXML
    public void ajouterButton(TextField name, TextField montant, ChoiceBox<String> choiceBox, Label labelDelName, Label labeladdName, Label montantAdd, boolean isRevenu){
        String nameString = name.getText();
        String montantString = montant.getText();
        labelDelName.setVisible(false);
        if(!montantString.isEmpty() && !nameString.isEmpty()){
            if(!montantString.matches("(\\d+([,]|[.])\\d+)|\\d+")){
                montant.clear();
                return;
            }
            montantString = montantString.replace(",",".");

            double montantValue = Double.parseDouble(montantString);
            controller.addRecurrent(new Montant(new Category(nameString),montantValue),isRevenu);


            labeladdName.setText(nameString+" de: ");
            montantAdd.setText(montantString+" € ajouté");
            labeladdName.setVisible(true);
            montantAdd.setVisible(true);

            name.clear();
            montant.clear();
            choiceBox.getItems().clear();
            display();
        }

    }

    public void supprimerButton(ChoiceBox<String> choiceBox, Label labelAddName, Label montantAdd, Label labelDelName, boolean isRevenu){
        labelAddName.setVisible(false);
        montantAdd.setVisible(false);
        if(!choiceBox.getItems().isEmpty()){
            controller.removeRecurrent(choiceBox.getSelectionModel().getSelectedItem(), isRevenu);

            labelDelName.setText(choiceBox.getSelectionModel().getSelectedItem()+" supprimé");
            labelDelName.setVisible(true);

            choiceBox.getItems().clear();
            display();
        }
    }

    @FXML
    public void ajouterCharge(){
        ajouterButton(chargeToAddTF, montantTF, choiceBoxCharge, chargeDelName, chargeAddName, montantAddLabel, false);
    }
    @FXML
    public void ajouterRevenu(){
        ajouterButton(revenuToAddTF, montantRTF, choiceBoxRevenu, revenuDelName, revenuAddName, montantRAddLabel, true);
    }


    @FXML
    public void supprimerCharge(){
        supprimerButton(choiceBoxCharge, chargeAddName, montantAddLabel, chargeDelName, false);
    }
    @FXML
    public void supprimerRevenu(){
        supprimerButton(choiceBoxRevenu, revenuAddName, montantRAddLabel, revenuDelName, true);
    }



    @FXML
    public void backWindow() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Scene.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
