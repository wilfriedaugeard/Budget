package sample.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class View {
    private Scene scene;
    private Parent root;
    private FXMLLoader fxmlLoader;


    /* Constructor */
    public View(String fxml_file, int width, int height) throws IOException {
        loadFXML(fxml_file);
        this.scene = new Scene(root,width, height);
    }

    /* Getters and setters */

    public Scene getScene() {
        return scene;
    }


    public void loadFXML(String fxml_file) throws IOException {
        this.fxmlLoader = new FXMLLoader(getClass().getResource(fxml_file));
        this.root = fxmlLoader.load();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

}
