package com.hockey.infrastructure.dto;

import java.sql.Date;

public class GameDTO {
    private Long gameId;
    private Long homeTeamId;
    private String homeTeamName;
    private Long guestTeamId;
    private String guestTeamName;
    private Integer homeTeamGoalCount;
    private Integer guestTeamGoalCount;
    private String homeTeamCityName;
    private String guestTeamCityName;
    private Date start;
    private Date end;

    public GameDTO() {

    }

    public GameDTO(Long gameId,
                   Long homeTeamId,
                   String homeTeamName,
                   Long guestTeamId,
                   String guestTeamName,
                   Integer homeTeamGoalCount,
                   Integer guestTeamGoalCount,
                   String homeTeamCityName,
                   String guestTeamCityName,
                   Date start,
                   Date end) {
        this.gameId = gameId;
        this.homeTeamId = homeTeamId;
        this.homeTeamName = homeTeamName;
        this.guestTeamId = guestTeamId;
        this.guestTeamName = guestTeamName;
        this.homeTeamGoalCount = homeTeamGoalCount;
        this.guestTeamGoalCount = guestTeamGoalCount;
        this.homeTeamCityName = homeTeamCityName;
        this.guestTeamCityName = guestTeamCityName;
        this.start = start;
        this.end = end;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Long homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public Long getGuestTeamId() {
        return guestTeamId;
    }

    public void setGuestTeamId(Long guestTeamId) {
        this.guestTeamId = guestTeamId;
    }

    public String getGuestTeamName() {
        return guestTeamName;
    }

    public void setGuestTeamName(String guestTeamName) {
        this.guestTeamName = guestTeamName;
    }

    public Integer getHomeTeamGoalCount() {
        return homeTeamGoalCount;
    }

    public void setHomeTeamGoalCount(Integer homeTeamGoalCount) {
        this.homeTeamGoalCount = homeTeamGoalCount;
    }

    public Integer getGuestTeamGoalCount() {
        return guestTeamGoalCount;
    }

    public void setGuestTeamGoalCount(Integer guestTeamGoalCount) {
        this.guestTeamGoalCount = guestTeamGoalCount;
    }

    public String getHomeTeamCityName() {
        return homeTeamCityName;
    }

    public void setHomeTeamCityName(String homeTeamCityName) {
        this.homeTeamCityName = homeTeamCityName;
    }

    public String getGuestTeamCityName() {
        return guestTeamCityName;
    }

    public void setGuestTeamCityName(String guestTeamCityName) {
        this.guestTeamCityName = guestTeamCityName;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "GameDTO{" +
                "gameId=" + gameId +
                ", homeTeamId=" + homeTeamId +
                ", homeTeamName='" + homeTeamName + '\'' +
                ", guestTeamId=" + guestTeamId +
                ", guestTeamName='" + guestTeamName + '\'' +
                ", homeTeamGoalCount=" + homeTeamGoalCount +
                ", guestTeamGoalCount=" + guestTeamGoalCount +
                ", homeTeamCityName='" + homeTeamCityName + '\'' +
                ", guestTeamCityName='" + guestTeamCityName + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
