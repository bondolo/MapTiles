package org.bondolo.pegs;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Peg Solitaire JavaFX application demonstration of Map Tiles
 */
public class Main {

    public static void main(String[] args) {
        Platform.startup(() -> {
            var label = new Label("Hello, World.");
            var pane = new StackPane(label);
            var scene = new Scene(pane, 640, 480);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        });
    }
}
