package org.junbang;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private static final int shadowSize = 20;
    private static Text textBlock = new Text();

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.DECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);

        StackPane stackPane = new StackPane(createShadowPane());
        stackPane.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.5);" +
                        "-fx-background-insets: " + shadowSize + ";"
        );
        textBlock.setFont(new Font(20));
        stackPane.getChildren().add(textBlock);

        scene = new Scene(stackPane, 800, 100);
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        stage.setX(200);
        stage.setY(bounds.getHeight() - 90);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void setText(String text) {
        textBlock.setText(text);
    }

    // Create a shadow effect as a halo around the pane and not within
    // the pane's content area.
    private Pane createShadowPane() {
        Pane shadowPane = new Pane();
        // a "real" app would do this in a CSS stylesheet.
        shadowPane.setStyle(
                "-fx-background-color: white;" +
                        "-fx-effect: dropshadow(gaussian, #fffabb, " + shadowSize + ", 0, 0, 0);" +
                        "-fx-background-insets: " + shadowSize + ";"
        );

        Rectangle innerRect = new Rectangle();
        Rectangle outerRect = new Rectangle();
        shadowPane.layoutBoundsProperty().addListener(
                (observable, oldBounds, newBounds) -> {
                    innerRect.relocate(
                            newBounds.getMinX() + shadowSize,
                            newBounds.getMinY() + shadowSize
                    );
                    innerRect.setWidth(newBounds.getWidth() - shadowSize * 2);
                    innerRect.setHeight(newBounds.getHeight() - shadowSize * 2);

                    outerRect.setWidth(newBounds.getWidth());
                    outerRect.setHeight(newBounds.getHeight());

                    Shape clip = Shape.subtract(outerRect, innerRect);
                    shadowPane.setClip(clip);
                }
        );

        return shadowPane;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        new Thread(() -> {
            launch();
        }).start();

        new Thread(() -> {
            PlayListener playListener = PlayListener.getInstance();
            playListener.loadLyric("memories", "maroon5", 30000);
            try {
                playListener.play(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "listener").start();
    }

}