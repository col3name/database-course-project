package com.hockey.infrastructure.dto;

import com.hockey.core.entity.league.Team;

public class PlayerTeamStandingsDto {
    private Long playerId;
    private Long seasonId;
    private String seasonStart;
    private String seasonEnd;
    private Team team;
    private Integer countGame;
    private Integer goalsScored;
    private Integer goalsMissed;
    private Integer difference;
    private Integer countWin;
    private Integer countLose;
    private Integer countOvertimeWin;
    private Integer countOvertimeLose;
    private Integer points;

    public PlayerTeamStandingsDto(Long playerId, Long seasonId, String seasonStart, String seasonEnd, Team team,
                                  Integer countGame, Integer goalsScored, Integer goalsMissed, Integer difference,
                                  Integer countWin, Integer countLose, Integer countOvertimeWin, Integer countOvertimeLose, Integer points) {
        this.playerId = playerId;
        this.seasonId = seasonId;
        this.seasonStart = seasonStart;
        this.seasonEnd = seasonEnd;
        this.team = team;
        this.countGame = countGame;
        this.goalsScored = goalsScored;
        this.goalsMissed = goalsMissed;
        this.difference = difference;
        this.countWin = countWin;
        this.countLose = countLose;
        this.countOvertimeWin = countOvertimeWin;
        this.countOvertimeLose = countOvertimeLose;
        this.points = points;
    }

    public PlayerTeamStandingsDto setPlayerId(Long playerId) {
        this.playerId = playerId;
        return this;
    }

    public PlayerTeamStandingsDto setSeasonId(Long seasonId) {
        this.seasonId = seasonId;
        return this;
    }

    public PlayerTeamStandingsDto setSeasonStart(String seasonStart) {
        this.seasonStart = seasonStart;
        return this;

    }

    public PlayerTeamStandingsDto setSeasonEnd(String seasonEnd) {
        this.seasonEnd = seasonEnd;
        return this;

    }

    public PlayerTeamStandingsDto setTeam(Team team) {
        this.team = team;
        return this;
    }

    public PlayerTeamStandingsDto setCountGame(Integer countGame) {
        this.countGame = countGame;
        return this;

    }

    public PlayerTeamStandingsDto setGoalsScored(Integer goalsScored) {
        this.goalsScored = goalsScored;
        return this;

    }

    public PlayerTeamStandingsDto setGoalsMissed(Integer goalsMissed) {
        this.goalsMissed = goalsMissed;
        return this;

    }

    public PlayerTeamStandingsDto setDifference(Integer difference) {
        this.difference = difference;
        return this;

    }

    public PlayerTeamStandingsDto setCountWin(Integer countWin) {
        this.countWin = countWin;
        return this;

    }

    public PlayerTeamStandingsDto setCountLose(Integer countLose) {
        this.countLose = countLose;
        return this;

    }

    public PlayerTeamStandingsDto setCountOvertimeWin(Integer countOvertimeWin) {
        this.countOvertimeWin = countOvertimeWin;
        return this;
    }

    public PlayerTeamStandingsDto setCountOvertimeLose(Integer countOvertimeLose) {
        this.countOvertimeLose = countOvertimeLose;
        return this;

    }

    public PlayerTeamStandingsDto setPoints(Integer points) {
        this.points = points;
        return this;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public String getSeasonStart() {
        return seasonStart;
    }

    public String getSeasonEnd() {
        return seasonEnd;
    }

    public Team getTeam() { return team;}

    public Integer getCountGame() {
        return countGame;
    }

    public Integer getGoalsScored() {
        return goalsScored;
    }

    public Integer getGoalsMissed() {
        return goalsMissed;
    }

    public Integer getDifference() {
        return difference;
    }

    public Integer getCountWin() {
        return countWin;
    }

    public Integer getCountLose() {
        return countLose;
    }

    public Integer getCountOvertimeWin() {
        return countOvertimeWin;
    }

    public Integer getCountOvertimeLose() {
        return countOvertimeLose;
    }

    public Integer getPoints() {
        return points;
    }

    public PlayerTeamStandingsDto build() {
        return new PlayerTeamStandingsDto(playerId,
                seasonId,
                seasonStart,
                seasonEnd,
                team,
                countGame,
                goalsScored,
                goalsMissed,
                difference,
                countWin,
                countLose,
                countOvertimeWin,
                countOvertimeLose,
                points);
    }
}
