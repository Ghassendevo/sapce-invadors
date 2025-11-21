package com.game.spaceinvadors.entities.player;

import com.game.spaceinvadors.entities.GameObject;
import com.game.spaceinvadors.entities.bullets.Bullet;
import com.game.spaceinvadors.factories.BulletFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private double speed = 4;

    private List<Bullet> bullets = new ArrayList<>();

    // Power-up type: "normal", "fast", "double", "shield"
    private String powerUp = "normal";

    public Player(double x, double y) {
        super(x, y);

        // Create the player shape
        Rectangle ship = new Rectangle(40, 20, Color.LIGHTGREEN);
        this.node = ship;

        // Initial placement
        setX(x);
        setY(y);
    }

    // -------------------
    //   GETTERS / SETTERS
    // -------------------

    public List<Bullet> getBullets() {
        return bullets;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setPowerUp(String powerUp) {
        this.powerUp = powerUp;
    }

    public String getCurrentPowerUp() {
        return powerUp;
    }

    // -------------------
    //       UPDATE
    // -------------------
    @Override
    public void update() {

        if (leftPressed) x -= speed;
        if (rightPressed) x += speed;

        // Screen boundaries (ensure player stays visible)
        if (x < 0) x = 0;
        if (x > 760) x = 760;

        // Apply movement
        setX(x);

        // ALWAYS bring player to front
        node.toFront();

        // Update bullets + remove off-screen bullets
        bullets.removeIf(Bullet::isOffScreen);
        bullets.forEach(Bullet::update);
    }

    // -------------------
    //    INPUT HANDLING
    // -------------------
    public void onKeyPressed(KeyCode key) {
        if (key == KeyCode.LEFT) leftPressed = true;
        if (key == KeyCode.RIGHT) rightPressed = true;

        if (key == KeyCode.SPACE) {
            Bullet b = shoot();
            bullets.add(b);

            // If double-shot, we add the second bullet
            if (powerUp.equals("double")) {
                Bullet second = BulletFactory.createNormalBullet(getX() + 5, getY() - 10);
                bullets.add(second);
            }
        }
    }

    public void onKeyReleased(KeyCode key) {
        if (key == KeyCode.LEFT) leftPressed = false;
        if (key == KeyCode.RIGHT) rightPressed = false;
    }

    // -------------------
    //      SHOOTING
    // -------------------

    public Bullet shoot() {
        double bulletX = this.getX() + 18;
        double bulletY = this.getY() - 15; // slightly lower so bullet starts at nose

        return switch (powerUp) {
            case "fast" -> BulletFactory.createFastBullet(bulletX, bulletY);
            case "double" -> BulletFactory.createNormalBullet(bulletX, bulletY);
            case "shield" -> BulletFactory.createNormalBullet(bulletX, bulletY);
            default -> BulletFactory.createNormalBullet(bulletX, bulletY);
        };
    }
}
