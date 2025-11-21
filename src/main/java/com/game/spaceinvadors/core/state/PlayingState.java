package com.game.spaceinvadors.core.state;

import com.game.spaceinvadors.core.GameManager;
import com.game.spaceinvadors.core.logger.GameLogger;
import com.game.spaceinvadors.entities.bullets.Bullet;
import com.game.spaceinvadors.entities.enemies.Enemy;
import com.game.spaceinvadors.entities.enemies.EnemyGroup;
import com.game.spaceinvadors.entities.player.Player;
import com.game.spaceinvadors.entities.powerups.PowerUp;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayingState implements GameState {

    private Player player;
    private EnemyGroup enemyGroup;
    private AnimationTimer gameLoop;

    // Power-ups
    private final List<PowerUp> powerUps = new ArrayList<>();
    private final Random rng = new Random();

    // HUD
    private int score = 0;
    private int lives = 3;

    private Text scoreText;
    private Text livesText;
    private Text powerUpText;

    // Waves
    private int wave = 1;

    @Override
    public void onEnter(Pane root, Scene scene) {

        root.setStyle("-fx-background-color: black;");

        // --- PLAYER ---
        player = new Player(380, 540);  // LOWERED for visibility
        root.getChildren().add(player.getNode());
        player.getNode().toFront();

        // --- FIRST WAVE ---
        spawnWave(root);

        // --- HUD ---
        setupHUD(root);

        // --- INPUT HANDLING ---
        scene.setOnKeyPressed(e -> player.onKeyPressed(e.getCode()));
        scene.setOnKeyReleased(e -> player.onKeyReleased(e.getCode()));

        // --- GAME LOOP ---
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {

                // --- PLAYER UPDATE + BULLETS ---
                player.update();

                // Ensure bullets added visually
                for (Bullet b : player.getBullets()) {
                    if (!root.getChildren().contains(b.getNode())) {
                        root.getChildren().add(b.getNode());
                        b.getNode().toBack(); // bullets behind player
                    }
                }

                // --- UPDATE ENEMIES ---
                enemyGroup.update();

                // --- BULLET ↔ ENEMY COLLISION ---
                for (Bullet b : player.getBullets()) {

                    Enemy hitEnemy = enemyGroup.getCollidedEnemy(b);
                    if (hitEnemy != null) {

                        hitEnemy.kill();
                        b.setY(-1000);   // Hide bullet

                        score += 100;
                        updateHUD();

                        GameLogger.getInstance().logEvent("Enemy killed | Score = " + score);
                        break;
                    }
                }

                // --- RANDOM POWER-UP SPAWN ---
                spawnPowerUp(root);

                // --- POWER-UP UPDATE ---
                powerUps.removeIf(PowerUp::isOffScreen);
                powerUps.forEach(PowerUp::update);

                // --- POWER-UP PICKUP ---
                checkPowerUpPickup();

                // --- CHECK WAVE COMPLETION ---
                if (enemyGroup.allDead()) {
                    wave++;

                    if (wave > 3) {
                        GameLogger.getInstance().logEvent("All waves cleared → Victory!");
                        GameManager.getInstance().changeState(new VictoryState());
                    } else {
                        spawnWave(root);
                    }
                }

                // --- GAME OVER ---
                if (enemyGroup.reachedBottom()) {
                    GameLogger.getInstance().logEvent("Enemy reached bottom → Game Over");
                    GameManager.getInstance().changeState(new GameOverState());
                }
            }
        };

        gameLoop.start();
    }

    // ---------------------------------------------------------
    //                     WAVE SPAWNING
    // ---------------------------------------------------------
    private void spawnWave(Pane root) {

        GameLogger.getInstance().logEvent("Starting Wave " + wave);

        enemyGroup = new EnemyGroup(); // NEW group

        switch (wave) {
            case 1 -> enemyGroup.spawnBasicWave(root);
            case 2 -> enemyGroup.spawnWideWave(root);
            case 3 -> enemyGroup.spawnFastWave(root);
        }
    }

    // ---------------------------------------------------------
    //                       HUD
    // ---------------------------------------------------------
    private void setupHUD(Pane root) {

        scoreText = new Text("Score: " + score);
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(Font.font("Arial", 20));
        scoreText.setTranslateX(10);
        scoreText.setTranslateY(25);

        livesText = new Text("Lives: " + lives);
        livesText.setFill(Color.WHITE);
        livesText.setFont(Font.font("Arial", 20));
        livesText.setTranslateX(10);
        livesText.setTranslateY(50);

        powerUpText = new Text("Power-Up: none");
        powerUpText.setFill(Color.WHITE);
        powerUpText.setFont(Font.font("Arial", 20));
        powerUpText.setTranslateX(600);
        powerUpText.setTranslateY(25);

        root.getChildren().addAll(scoreText, livesText, powerUpText);
    }

    private void updateHUD() {
        scoreText.setText("Score: " + score);
        livesText.setText("Lives: " + lives);
        powerUpText.setText("Power-Up: " + player.getCurrentPowerUp());
    }

    // ---------------------------------------------------------
    //                     POWER UPS
    // ---------------------------------------------------------
    private void spawnPowerUp(Pane root) {
        if (rng.nextInt(550) == 0) { // LOWER SPAWN RATE
            String[] types = {"fast", "double", "shield", "speed"};
            String type = types[rng.nextInt(types.length)];

            PowerUp p = new PowerUp(rng.nextInt(760), 0, type);
            powerUps.add(p);
            root.getChildren().add(p.getNode());
            p.getNode().toBack();
        }
    }

    private void checkPowerUpPickup() {
        for (PowerUp p : new ArrayList<>(powerUps)) {

            if (player.getNode().getBoundsInParent().intersects(p.getNode().getBoundsInParent())) {

                switch (p.getType()) {
                    case "fast" -> player.setPowerUp("fast");
                    case "double" -> player.setPowerUp("double");
                    case "shield" -> player.setPowerUp("shield");
                    case "speed" -> player.setSpeed(player.getSpeed() * 1.5);
                }

                GameLogger.getInstance().logEvent("Power-up collected: " + p.getType());

                p.getNode().setVisible(false);
                powerUps.remove(p);
            }
        }
    }

    // ---------------------------------------------------------
    //                       EXIT
    // ---------------------------------------------------------
    @Override
    public void onExit() {
        if (gameLoop != null) gameLoop.stop();
    }

    @Override
    public String getName() {
        return "PLAYING";
    }
}
