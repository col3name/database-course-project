package com.hockey.core.entity.player;

public enum ShootDirection {
    LEFT(false),
    RIGHT(true);

    private final boolean isRightShoot;

    ShootDirection(boolean isRightShoot) {
        this.isRightShoot = isRightShoot;
    }

    public boolean isRightShoot() {
        return isRightShoot;
    }
}
