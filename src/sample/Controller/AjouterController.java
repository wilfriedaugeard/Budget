package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.Model.Category;
import sample.Model.ICategory;
import sample.Model.IPeriode;
import sample.Model.Montant;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterController implements Initializable {
    private IController controller;

    @FXML
    private AnchorPane rootPane;
    // Revenus section
    @FXML
    private TextField revenusTextField;
    @FXML
    private ChoiceBox<String> revenusChoiceBox;
    @FXML
    private Button plusRevButton;
    @FXML
    private Button createRevButton;
    @FXML
    private TextField createRevCatTF;
    @FXML
    private Label nouveauRevenuLabel;
    @FXML
    private Label revenuAjouteLabel;
    @FXML
    private Label euroRevenuLabel;
    // Depenses section
    @FXML
    private TextField depensesTextField;
    @FXML
    private ChoiceBox<String> depensesChoiceBox;
    @FXML
    private Button plusDepButton;
    @FXML
    private Button createDepButton;
    @FXML
    private TextField createDepCatTF;
    @FXML
    private Label nouvelleDepenseLabel;
    @FXML
    private Label depenseAjouteLabel;
    @FXML
    private Label euroDepenseLabel;
    // Ajustemement
    @FXML
    private TextField soldeTF;
    @FXML
    private TextField epargneTF;
    @FXML
    private Label labelSoldeAjust;
    @FXML
    private Label labelEpargneAjust;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = Controller.getController();
        display();
    }

    public void display(){
        revenusChoiceBox.getItems().clear();
        for(ICategory category : controller.getRevenuesCategory().getChildren()){
            revenusChoiceBox.getItems().add(category.getName());
        }
        revenusChoiceBox.getSelectionModel().selectFirst();
        depensesChoiceBox.getItems().clear();
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

    @FXML
    public void ajouterRevenus(){
        String montantString = revenusTextField.getText();
        if(!montantString.isEmpty()){
            if(!montantString.matches("(\\d+([,]|[.])\\d+)|\\d+")){
                revenusTextField.clear();
                return;
            }
            montantString = montantString.replace(",",".");

            double montant = Double.parseDouble(montantString);
            if(montant == 0){
                revenusTextField.clear();
                return;
            }
            int choice = revenusChoiceBox.getSelectionModel().getSelectedIndex();
            int i = controller.getYearCursor();
            IPeriode m = controller.getGlobalPeriode().get(i).getChild(controller.getMonthCursor());
            m.addRevenues(new Montant(controller.getRevenuesCategory().getChild(choice), montant));
            if(controller.getRevenuesCategory().getChild(choice).getName().equals("Mouvement épargne")){
                m.addEpargne(-montant);
            }
            revenuAjouteLabel.setText(montantString);

            nouveauRevenuLabel.setVisible(true);
            revenuAjouteLabel.setVisible(true);
            euroRevenuLabel.setVisible(true);

            revenusTextField.clear();
            controller.saveState();
        }
    }

    @FXML
    public void ajouterDepenses(){
        String montantString = depensesTextField.getText();
        if(!montantString.isEmpty()){
            if(!montantString.matches("(\\d+([,]|[.])\\d+)|\\d+")){
                revenusTextField.clear();
                return;
            }
            montantString = montantString.replace(",",".");

            double montant = Double.parseDouble(montantString);
            if(montant == 0){
                depensesTextField.clear();
                return;
            }
            int choice = depensesChoiceBox.getSelectionModel().getSelectedIndex();
            int i = controller.getYearCursor();
            IPeriode m = controller.getGlobalPeriode().get(i).getChild(controller.getMonthCursor());
            m.addDepenses(new Montant(controller.getDepensesCategory().getChild(choice), montant));
            if(controller.getDepensesCategory().getChild(choice).getName().equals("Mouvement épargne")){
                m.addEpargne(montant);
            }
            depenseAjouteLabel.setText(montantString);

            nouvelleDepenseLabel.setVisible(true);
            depenseAjouteLabel.setVisible(true);
            euroDepenseLabel.setVisible(true);

            depensesTextField.clear();
            controller.saveState();
        }
    }

    @FXML
    public void showCreateRevCate(){
        showCreateCategory(plusRevButton, createRevCatTF, createRevButton);
    }
    @FXML
    public void showCreateDepCate(){
        showCreateCategory(plusDepButton, createDepCatTF, createDepButton);
    }


    public void showCreateCategory(Button plusBtn, TextField tf, Button createBtn){
        if(!plusBtn.getText().equals("+")){
            plusBtn.setText("+");
            tf.setVisible(false);
            createBtn.setVisible(false);
            return;
        }
        plusBtn.setText(" - ");
        tf.setVisible(true);
        createBtn.setVisible(true);
    }

    @FXML
    public void createRevCategory(){
        createCategory(controller.getRevenuesCategory(), createRevCatTF, plusRevButton, createRevButton, "+");
        controller.saveState();
    }
    @FXML
    public void createDepCategory(){
        createCategory(controller.getDepensesCategory(), createDepCatTF, plusDepButton, createDepButton, "+");
        controller.saveState();
    }

    public void createCategory(ICategory globalCategory, TextField tf, Button plusBtn, Button createBtn, String text){
        String stringCategory= tf.getText();
        if(!stringCategory.isEmpty()){
            globalCategory.add(new Category(stringCategory));
            display();
        }
        plusBtn.setText(text);
        tf.setVisible(false);
        createBtn.setVisible(false);
    }


    public void ajust(TextField tf, boolean isBudget, Label label){
        String string = tf.getText();
        if(!string.isEmpty()){
            if(!string.matches("(\\d+([,]|[.])\\d+)|\\d+")){
                tf.clear();
            }else{
                double value = Double.parseDouble(string);
                if(value == 0){
                    tf.clear();
                }else{
                    if(isBudget) {
                        controller.getCurrentMonth().setBudget(value);
                        label.setText("Ajustement du budget: "+value+" €");
                    }else{
                        controller.getCurrentMonth().setEpargne(value);
                        label.setText("Ajustement de l'épargne: "+value+" €");
                    }
                    label.setVisible(true);
                }
            }tf.clear();
        }
    }

    @FXML
    public void ajustAndDisplay(){
       labelSoldeAjust.setVisible(false);
       labelEpargneAjust.setVisible(false);
       ajust(soldeTF, true, labelSoldeAjust);
       ajust(epargneTF, false, labelEpargneAjust);
       controller.saveState();
    }

}
