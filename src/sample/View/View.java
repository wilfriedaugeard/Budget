package sample.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class View {
    private Scene scene;
    private Parent root;
    private int width;
    private int height;



    /* Constructor */
    public View(String fxml_file, int width, int height) throws IOException {
        this.width = width;
        this.height = height;


        this.root = FXMLLoader.load(getClass().getResource(fxml_file));
        this.scene = new Scene(root,width, height);
    }

    /* Getters and setters */

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

}
