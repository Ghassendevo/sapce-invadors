package com.game.spaceinvadors.core.state;

import com.game.spaceinvadors.core.GameManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MenuState implements GameState {

    @Override
    public void onEnter(Pane root, Scene scene) {

        root.setStyle("-fx-background-color: black;");

        // -------------------------
        //   TITLE
        // -------------------------
        Text title = new Text("SPACE INVADERS");
        title.setFill(Color.LIMEGREEN);
        title.setFont(Font.font("Arial", 50));
        title.setStyle("-fx-effect: dropshadow(gaussian, lime, 20, 0.7, 0, 0);");

        // -------------------------
        //   BUTTONS
        // -------------------------
        Button startBtn = new Button("Start Game");
        Button quitBtn = new Button("Quit");

        startBtn.setPrefWidth(200);
        quitBtn.setPrefWidth(200);

        startBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: limegreen;" +
                        "-fx-border-width: 2;" +
                        "-fx-text-fill: limegreen;" +
                        "-fx-font-size: 18;"
        );

        quitBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: limegreen;" +
                        "-fx-border-width: 2;" +
                        "-fx-text-fill: limegreen;" +
                        "-fx-font-size: 18;"
        );

        startBtn.setOnAction(e -> GameManager.getInstance().changeState(new PlayingState()));
        quitBtn.setOnAction(e -> System.exit(0));

        // -------------------------
        //  LAYOUT
        // -------------------------
        VBox box = new VBox(25, title, startBtn, quitBtn);
        box.setAlignment(Pos.CENTER);

        // FIX: Proper centering (Pane does NOT center)
        StackPane wrapper = new StackPane(box);
        wrapper.setPrefSize(root.getPrefWidth(), root.getPrefHeight());
        wrapper.setAlignment(Pos.CENTER);

        root.getChildren().add(wrapper);
    }

    @Override
    public void onExit() {}

    @Override
    public String getName() {
        return "MENU";
    }
}
