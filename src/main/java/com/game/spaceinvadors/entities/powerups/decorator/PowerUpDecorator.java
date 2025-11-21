package com.game.spaceinvadors.entities.powerups.decorator;

import com.game.spaceinvadors.entities.player.Player;

public abstract class PowerUpDecorator extends Player {

    protected Player wrapped;

    public PowerUpDecorator(Player wrapped) {
        super(wrapped.getX(), wrapped.getY());
        this.wrapped = wrapped;
        this.node = wrapped.getNode(); // keep same visual
    }

    @Override
    public void update() {
        wrapped.update();
        applyEffect();
    }

    protected abstract void applyEffect();
}
