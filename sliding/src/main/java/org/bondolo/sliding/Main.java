package org.bondolo.sliding;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Sliding Square JavaFX application demonstration of Map Tiles
 */
public class Main {

    public static void main(String[] args) {

        Platform.startup(() -> {
            Font font = Font.font(java.awt.Font.SANS_SERIF, FontWeight.EXTRA_BOLD, 60);
            Label[] tiles = Stream.concat(Stream.of(""),
                    IntStream.rangeClosed(1, 15)
                            .mapToObj(Integer::toString))
                    .map(s -> new Label(s))
                    .peek(l -> l.setFont(font))
                    .peek(l -> l.setAlignment(Pos.CENTER))
                    .toArray(Label[]::new);

            var pane = new TilePane(Orientation.HORIZONTAL);
            pane.setTileAlignment(Pos.CENTER_LEFT);
            pane.setHgap(4);
            pane.setVgap(4);
            pane.setPrefRows(4);
            pane.setPrefColumns(4);
            pane.setMinHeight(480);
            pane.setMaxHeight(480);
            pane.setMinWidth(480);
            pane.setMaxWidth(480);
            pane.getChildren().addAll(tiles);
            var scene = new Scene(pane, 480, 480);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        });
    }
}
