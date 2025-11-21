package com.game.spaceinvadors.entities;

import javafx.scene.Node;

public abstract class GameObject {

    protected double x;
    protected double y;
    protected Node node;

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Called every frame (game loop) */
    public abstract void update();

    /** Returns the JavaFX visual node */
    public Node getNode() {
        return node;
    }

    public double getX() { return x; }

    public double getY() { return y; }

    public void setX(double x) {
        this.x = x;
        node.setTranslateX(x);
    }

    public void setY(double y) {
        this.y = y;
        node.setTranslateY(y);
    }
}
