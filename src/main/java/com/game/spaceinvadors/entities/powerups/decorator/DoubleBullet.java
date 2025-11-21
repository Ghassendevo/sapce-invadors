package com.game.spaceinvadors.entities.powerups.decorator;

import com.game.spaceinvadors.entities.bullets.Bullet;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DoubleBullet extends BulletDecorator {

    private boolean createdExtra = false;
    private Bullet extraBullet;

    public DoubleBullet(Bullet wrapped) {
        super(wrapped);
    }

    @Override
    protected void applyEffect() {
        if (!createdExtra) {
            extraBullet = new Bullet(wrapped.getX() + 20, wrapped.getY());
            Rectangle shape = new Rectangle(5, 10, Color.ORANGE);
            extraBullet.getNode().setTranslateX(extraBullet.getX());
            extraBullet.getNode().setTranslateY(extraBullet.getY());
            createdExtra = true;
        }
    }

    public Bullet getExtraBullet() {
        return extraBullet;
    }
}
