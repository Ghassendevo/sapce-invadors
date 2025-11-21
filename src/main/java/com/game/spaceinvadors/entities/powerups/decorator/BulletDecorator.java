package com.game.spaceinvadors.entities.powerups.decorator;

import com.game.spaceinvadors.entities.bullets.Bullet;
import javafx.scene.Node;

public abstract class BulletDecorator extends Bullet {

    protected Bullet wrapped;

    public BulletDecorator(Bullet wrapped) {
        super(wrapped.getX(), wrapped.getY());
        this.wrapped = wrapped;
        this.node = wrapped.getNode();
    }

    @Override
    public Node getNode() {
        return wrapped.getNode();
    }

    @Override
    public void update() {
        wrapped.update();
        applyEffect();
    }

    protected abstract void applyEffect();
}
