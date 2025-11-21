package com.game.spaceinvadors.entities.powerups.decorator;

import com.game.spaceinvadors.entities.player.Player;

public class SpeedBoost extends PowerUpDecorator {

    private int duration = 350;

    public SpeedBoost(Player wrapped) {
        super(wrapped);
        wrapped.setSpeed(wrapped.getSpeed() * 1.8); // increase movement
    }

    @Override
    protected void applyEffect() {
        duration--;

        if (duration <= 0) {
            wrapped.setSpeed(wrapped.getSpeed() / 1.8); // back to normal
        }
    }
}
