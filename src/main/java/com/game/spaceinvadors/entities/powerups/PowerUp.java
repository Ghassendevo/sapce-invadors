package com.game.spaceinvadors.entities.powerups;

import com.game.spaceinvadors.entities.GameObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PowerUp extends GameObject {

    private final String type;

    public PowerUp(double x, double y, String type) {
        super(x, y);
        this.type = type;

        // Smaller, cleaner visual size
        Circle shape = new Circle(7); // was 12 (too big)

        shape.setFill(switch (type) {
            case "fast" -> Color.ORANGE;
            case "double" -> Color.CYAN;
            case "shield" -> Color.DEEPSKYBLUE;
            case "speed" -> Color.YELLOWGREEN;
            default -> Color.WHITE;
        });

        this.node = shape;

        // Use GameObject's setters
        setX(x);
        setY(y);

        // Keep power-ups BEHIND the player
        node.toBack();
    }

    public String getType() {
        return type;
    }

    @Override
    public void update() {
        // PowerUp slowly falls down
        y += 0.6;  // was 1.2 (too fast)
        setY(y);
    }

    public boolean isOffScreen() {
        return y > 600;
    }
}
