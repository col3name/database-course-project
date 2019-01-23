package com.hockey.core.entity.player;

import com.hockey.core.entity.BaseEntity;

public class PlayerPosition extends BaseEntity {
    public static final String TABLE_NAME = "player_position";

    private String fullName;

    private String shortName;

    public PlayerPosition() {
    }

    public PlayerPosition(String fullName) {
        this.fullName = fullName;
    }

    public PlayerPosition(String fullName, String shortName) {
        this(fullName);
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
