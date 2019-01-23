package com.hockey.core.entity.league;

import com.hockey.core.entity.BaseEntity;

import java.util.Date;

public class Game extends BaseEntity {
    private final Long seasonId;
    private final Long guestTeamId;
    private final Long homeTeamId;
    private final Date time;
    private final Integer guestTeamGoalCount;
    private final Integer homeTeamGoalCount;
    private final Date startDate;
    private final Date endDate;

    public Game(Long seasonId, Long guestTeamId, Long homeTeamId, Date time, Integer guestTeamGoalCount, Integer homeTeamGoalCount, Date startDate, Date endDate) {
        this.seasonId = seasonId;
        this.guestTeamId = guestTeamId;
        this.homeTeamId = homeTeamId;
        this.time = time;
        this.guestTeamGoalCount = guestTeamGoalCount;
        this.homeTeamGoalCount = homeTeamGoalCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public Long getGuestTeamId() {
        return guestTeamId;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public Date getTime() {
        return time;
    }

    public Integer getGuestTeamGoalCount() {
        return guestTeamGoalCount;
    }

    public Integer getHomeTeamGoalCount() {
        return homeTeamGoalCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
