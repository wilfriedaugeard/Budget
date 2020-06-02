package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Controller.Controller;
import sample.Controller.IController;
import sample.Model.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        IController controller = new Controller();
        controller.initializeView();

        primaryStage.setScene(controller.getScene());
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
