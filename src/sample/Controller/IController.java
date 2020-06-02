package sample.Controller;

import javafx.scene.Scene;

import java.io.IOException;

public interface IController {
    public void initializeView() throws IOException;
    public Scene getScene();
}
