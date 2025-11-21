package com.game.spaceinvadors.entities.enemies;

import com.game.spaceinvadors.entities.bullets.Bullet;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public class EnemyGroup {

    private final List<Enemy> enemies = new ArrayList<>();
    private double direction = 1; // 1 = right, -1 = left
    private double baseSpeed = 1.0;

    private int stepCounter = 0; // for slow downward drift

    // ------------------------------
    //          WAVE 1 (Basic)
    // ------------------------------
    public void spawnBasicWave(Pane root) {
        baseSpeed = 1.0;
        enemies.clear();

        for (int i = 0; i < 8; i++) {
            Enemy e = new Enemy(80 + i * 70, 70); // perfectly aligned at top
            enemies.add(e);
            root.getChildren().add(e.getNode());
        }
    }

    // ------------------------------
    //          WAVE 2 (Wide)
    // ------------------------------
    public void spawnWideWave(Pane root) {
        baseSpeed = 1.3;
        enemies.clear();

        for (int i = 0; i < 12; i++) {
            Enemy e = new Enemy(40 + i * 60, 100); // centered nicely
            enemies.add(e);
            root.getChildren().add(e.getNode());
        }
    }

    // ------------------------------
    //          WAVE 3 (Fast)
    // ------------------------------
    public void spawnFastWave(Pane root) {
        baseSpeed = 2.0;
        enemies.clear();

        for (int i = 0; i < 8; i++) {
            Enemy e = new Enemy(80 + i * 70, 60); // slightly higher for difficulty
            enemies.add(e);
            root.getChildren().add(e.getNode());
        }
    }

    // ------------------------------
    //         COLLISION DETECTION
    // ------------------------------
    public Enemy getCollidedEnemy(Bullet bullet) {
        for (Enemy e : enemies) {
            if (e.isAlive() && e.collidesWith(bullet)) {
                return e;
            }
        }
        return null;
    }

    // ------------------------------
    //           GROUP UPDATE
    // ------------------------------
    public void update() {
        boolean changeDirection = false;

        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) continue;

            // Horizontal movement
            double newX = enemy.getX() + (baseSpeed * direction);
            enemy.setX(newX);

            // Border detection triggers direction swap
            if (enemy.getX() < 10 || enemy.getX() > 760) {
                changeDirection = true;
            }
        }

        // When a border is hit → reverse and move DOWN
        if (changeDirection) {
            direction *= -1;

            for (Enemy enemy : enemies) {
                enemy.setY(enemy.getY() + 20); // classic step down
            }
        }

        // Slow downward drift every ~200 frames
        stepCounter++;
        if (stepCounter % 200 == 0) {
            for (Enemy enemy : enemies) {
                enemy.setY(enemy.getY() + 5);
            }
        }
    }

    // ------------------------------
    //         STATE CHECKS
    // ------------------------------
    public boolean allDead() {
        return enemies.stream().noneMatch(Enemy::isAlive);
    }

    public boolean reachedBottom() {
        // Enemy touching bottom of screen (600px window)
        return enemies.stream().anyMatch(e -> e.getY() > 520);
    }
}
