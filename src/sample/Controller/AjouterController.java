package sample.Controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AjouterController implements Initializable {
    private IController controller;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = Controller.getController();
    }

    
}
