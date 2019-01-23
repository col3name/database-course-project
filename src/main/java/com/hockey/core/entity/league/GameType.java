package com.hockey.core.entity.league;

public enum GameType {
    HOME("home"),
    GUEST("guest");

    private final String name;

    GameType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GameType{" +
                "name='" + name + '\'' +
                '}';
    }
}
