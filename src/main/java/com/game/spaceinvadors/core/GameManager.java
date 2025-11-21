package com.game.spaceinvadors.core;

import com.game.spaceinvadors.core.state.GameState;
import com.game.spaceinvadors.core.state.MenuState;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.game.spaceinvadors.core.logger.GameLogger;
public class GameManager {

    private static GameManager instance;
    private Stage mainStage;
    private GameState currentState;

    private GameManager() {}

    public static GameManager getInstance() {
        if (instance == null) instance = new GameManager();
        return instance;
    }

    public void init(Stage stage) {
        this.mainStage = stage;
        changeState(new MenuState());
        stage.setTitle("Space Invaders");
        stage.show();
    }

    public void changeState(GameState newState) {

        if (currentState != null) {
            currentState.onExit();
            GameLogger.getInstance().logState(currentState.getName(), newState.getName());
        } else {
            GameLogger.getInstance().logState("NONE", newState.getName());
        }

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);

        currentState = newState;
        currentState.onEnter(root, scene);

        mainStage.setScene(scene);
    }

}
