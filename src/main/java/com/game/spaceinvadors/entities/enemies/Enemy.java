package com.game.spaceinvadors.entities.enemies;

import com.game.spaceinvadors.entities.GameObject;
import com.game.spaceinvadors.entities.bullets.Bullet;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends GameObject {

    private boolean alive = true;

    public Enemy(double x, double y) {
        super(x, y);

        // Smaller / cleaner red enemy sprite
        Rectangle body = new Rectangle(28, 18, Color.RED);
        this.node = body;

        // Use GameObject setters to ensure future consistency
        setX(x);
        setY(y);

        // Keep enemies behind player, above bullets
        node.toBack();
    }

    // ---------------------------------------------
    //           COLLISION CHECK
    // ---------------------------------------------
    public boolean collidesWith(Bullet b) {
        return alive &&
                node.getBoundsInParent().intersects(
                        b.getNode().getBoundsInParent()
                );
    }

    @Override
    public void update() {
        // Movement handled in EnemyGroup
    }

    // ---------------------------------------------
    //              STATE CONTROL
    // ---------------------------------------------
    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        this.alive = false;

        // Enemy disappears visually but stays for logic until wave reset
        node.setVisible(false);
    }
}
