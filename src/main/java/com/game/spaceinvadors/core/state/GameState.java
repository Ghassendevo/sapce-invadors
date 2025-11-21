package com.game.spaceinvadors.core.state;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public interface GameState {
    void onEnter(Pane root, Scene scene);
    void onExit();
    String getName();
}
