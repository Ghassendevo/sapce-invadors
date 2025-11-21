package com.game.spaceinvadors.entities.powerups.decorator;

import com.game.spaceinvadors.entities.player.Player;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Shield extends PowerUpDecorator {

    private Circle aura;
    private int duration = 400; // frames

    public Shield(Player wrapped) {
        super(wrapped);

        aura = new Circle(30, Color.CYAN);
        aura.setOpacity(0.4);
        aura.setTranslateX(wrapped.getX() + 20);
        aura.setTranslateY(wrapped.getY() + 10);
        wrapped.getNode().getParent().getChildrenUnmodifiable().add(aura);
    }

    @Override
    protected void applyEffect() {
        // Follow player
        aura.setTranslateX(wrapped.getX() + 20);
        aura.setTranslateY(wrapped.getY() + 10);

        duration--;

        if (duration <= 0) {
            aura.setVisible(false);
        }
    }
}
