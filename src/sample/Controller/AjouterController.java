package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.Model.ICategory;
import sample.Model.IPeriode;
import sample.Model.Montant;
import sample.Model.Month;

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
    private ChoiceBox<String> revenusChoiceBox;
    @FXML
    private Label nouveauRevenuLabel;
    @FXML
    private Label revenuAjouteLabel;
    @FXML
    private Label euroRevenuLabel;
    @FXML
    private TextField depensesTextField;
    @FXML
    private ChoiceBox<String> depensesChoiceBox;

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
            System.out.println(controller.getRevenuesCategory().getChild(choice).getName());
            revenuAjouteLabel.setText(montantString);

            nouveauRevenuLabel.setVisible(true);
            revenuAjouteLabel.setVisible(true);
            euroRevenuLabel.setVisible(true);

            revenusTextField.clear();
        }
    }

}
