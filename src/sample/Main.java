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
    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";




    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Budget");
        BorderPane root = new BorderPane();
        Scene scene  = new Scene(root,800,600);
        primaryStage.setScene(scene);
        primaryStage.show();

        IController controller = new Controller();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
