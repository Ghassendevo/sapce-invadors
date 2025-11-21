package com.game.spaceinvadors.factories;

import com.game.spaceinvadors.entities.bullets.Bullet;

public class BulletFactory {

    // Normal Player Shot
    public static Bullet createNormalBullet(double x, double y) {
        return new Bullet(x, y);
    }

    // Fast Shot (higher speed)
    public static Bullet createFastBullet(double x, double y) {
        Bullet b = new Bullet(x, y);
        b.setSpeed(-11); // much faster upward
        return b;
    }

    // Double Shot = just a normal bullet
    // (Player.java already creates 2 bullets for double power-up)
    public static Bullet createDoubleBullet(double x, double y) {
        return new Bullet(x, y);
    }
}
