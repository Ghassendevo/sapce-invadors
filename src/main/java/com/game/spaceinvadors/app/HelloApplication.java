package com.game.spaceinvadors.app;

import com.game.spaceinvadors.core.GameManager;
import com.game.spaceinvadors.core.logger.GameLogger;
import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        GameManager.getInstance().init(stage);
        GameLogger.getInstance().logEvent("Game initialized.");

    }

    public static void main(String[] args) {
        launch();
    }
}
