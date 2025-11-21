package com.game.spaceinvadors.core.state;

import com.game.spaceinvadors.core.GameManager;
import com.game.spaceinvadors.core.logger.GameLogger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class VictoryState implements GameState {

    @Override
    public void onEnter(Pane root, Scene scene) {

        root.setStyle("-fx-background-color: black;");

        // --- Title ---
        Text title = new Text("YOU WIN!");
        title.setFill(Color.LIGHTGREEN);
        title.setFont(Font.font("Arial", 60));

        // --- Buttons ---
        Button retryBtn = new Button("Play Again");
        Button menuBtn = new Button("Return to Menu");

        retryBtn.setStyle("-fx-font-size: 20px; -fx-padding: 10;");
        menuBtn.setStyle("-fx-font-size: 20px; -fx-padding: 10;");

        retryBtn.setOnAction(e -> {
            GameLogger.getInstance().logEvent("Retry selected (Victory → PlayingState)");
            GameManager.getInstance().changeState(new PlayingState());
        });

        menuBtn.setOnAction(e -> {
            GameLogger.getInstance().logEvent("Returning to Menu from VictoryState");
            GameManager.getInstance().changeState(new MenuState());
        });

        // --- Layout ---
        VBox box = new VBox(20, title, retryBtn, menuBtn);
        box.setAlignment(Pos.CENTER);

        root.getChildren().add(box);
    }

    @Override
    public void onExit() {
        // No cleanup needed
    }

    @Override
    public String getName() {
        return "VICTORY";
    }
}
