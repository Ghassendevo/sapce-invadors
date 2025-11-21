package com.game.spaceinvadors.entities.bullets;

import com.game.spaceinvadors.entities.GameObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet extends GameObject {

    private double speed = -7;  // default upward speed

    public Bullet(double x, double y) {
        super(x, y);

        Rectangle shape = new Rectangle(4, 10, Color.YELLOW);
        this.node = shape;

        setX(x);
        setY(y);

        node.toBack();
    }

    // NEW METHOD — needed by BulletFactory
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void update() {
        y += speed;
        setY(y);
    }

    public boolean isOffScreen() {
        return y < -20;
    }
}
