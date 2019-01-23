package com.hockey.core.entity.league;

import java.util.Date;

public class GameBuilder {
    private Long seasonId = 0L;
    private Long guestTeamId = 0L;
    private Long homeTeamId = 0L;
    private Date time = new Date(System.currentTimeMillis());
    private Integer guestTeamGoalCount = 0;
    private Integer homeTeamGoalCount = 0;
    private Date startDate = new Date(System.currentTimeMillis());
    private Date endDate = new Date(System.currentTimeMillis());

    public GameBuilder setSeasonId(Long seasonId) {
        this.seasonId = seasonId;
        return this;
    }

    public GameBuilder setGuestTeamId(Long guestTeamId) {
        this.guestTeamId = guestTeamId;
        return this;
    }

    public GameBuilder setHomeTeamId(Long homeTeamId) {
        this.homeTeamId = homeTeamId;
        return this;
    }

    public GameBuilder setTime(Date time) {
        this.time = time;
        return this;
    }

    public GameBuilder setGuestTeamGoalCount(Integer guestTeamGoalCount) {
        this.guestTeamGoalCount = guestTeamGoalCount;
        return this;
    }

    public GameBuilder setHomeTeamGoalCount(Integer homeTeamGoalCount) {
        this.homeTeamGoalCount = homeTeamGoalCount;
        return this;
    }

    public GameBuilder setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public GameBuilder setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public Game createGame() {
        return new Game(seasonId, guestTeamId, homeTeamId, time, guestTeamGoalCount, homeTeamGoalCount, startDate, endDate);
    }
}