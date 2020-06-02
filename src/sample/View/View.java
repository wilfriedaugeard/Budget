package sample.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.awt.*;
import java.io.IOException;

public class View {
    private Scene scene;
    private Parent root;
    private int width;
    private int height;

    @FXML
    private Label month;

    /* Constructor */
    public View(String fxml_file, int width, int height) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(fxml_file));
        this.width = width;
        this.height = height;
        this.scene = new Scene(root,width, height);
    }

    /* Getters and setters */

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Label getMonth() {
        return month;
    }

    public void setMonth(Label month) {
        this.month = month;
    }
}
