package com.hockey.application.config;

public class Constants {
    private Constants() {
    }

    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/hockey_protocol";
    public static final String DATABASE_USER = "root";
    public static final String DATABASE_PASSWORD = "qwerty";

    public static final String COUNT_PLAYER_ON_PAGE = "30";
    public static final String START_PAGE = "1";

    public static final int GAME_COUNT = 5;
}
