package com.game.spaceinvadors.entities.powerups.decorator;

import com.game.spaceinvadors.entities.bullets.Bullet;

public class FastBullet extends BulletDecorator {

    public FastBullet(Bullet wrapped) {
        super(wrapped);
    }

    @Override
    protected void applyEffect() {
        wrapped.setY(wrapped.getY() - 4); // extra speed
    }
}
