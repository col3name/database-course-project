package com.hockey.infrastructure.dto;

public class StandingDTO {
    private Long teamId;
    private String teamName;
    private Integer gameCount;
    private Integer goalsScored;
    private Integer goalsMissed;
    private Integer countWin;
    private Integer countLose;
    private Integer countOvertimeWin;
    private Integer countOvertimeLose;
    private Integer points;

    public StandingDTO(Long teamId,
                       String teamName,
                       Integer gameCount,
                       Integer goalsScored,
                       Integer goalsMissed,
                       Integer countWin,
                       Integer countLose,
                       Integer countOvertimeWin,
                       Integer countOvertimeLose,
                       Integer points) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.gameCount = gameCount;
        this.goalsScored = goalsScored;
        this.goalsMissed = goalsMissed;
        this.countWin = countWin;
        this.countLose = countLose;
        this.countOvertimeWin = countOvertimeWin;
        this.countOvertimeLose = countOvertimeLose;
        this.points = points;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getGameCount() {
        return gameCount;
    }

    public void setGameCount(Integer gameCount) {
        this.gameCount = gameCount;
    }

    public Integer getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(Integer goalsScored) {
        this.goalsScored = goalsScored;
    }

    public Integer getGoalsMissed() {
        return goalsMissed;
    }

    public void setGoalsMissed(Integer goalsMissed) {
        this.goalsMissed = goalsMissed;
    }

    public Integer getCountWin() {
        return countWin;
    }

    public void setCountWin(Integer countWin) {
        this.countWin = countWin;
    }

    public Integer getCountLose() {
        return countLose;
    }

    public void setCountLose(Integer countLose) {
        this.countLose = countLose;
    }

    public Integer getCountOvertimeWin() {
        return countOvertimeWin;
    }

    public void setCountOvertimeWin(Integer countOvertimeWin) {
        this.countOvertimeWin = countOvertimeWin;
    }

    public Integer getCountOvertimeLose() {
        return countOvertimeLose;
    }

    public void setCountOvertimeLose(Integer countOvertimeLose) {
        this.countOvertimeLose = countOvertimeLose;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "StandingDTO{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", gameCount=" + gameCount +
                ", goalsScored=" + goalsScored +
                ", goalsMissed=" + goalsMissed +
                ", countWin=" + countWin +
                ", countLose=" + countLose +
                ", countOvertimeWin=" + countOvertimeWin +
                ", countOvertimeLose=" + countOvertimeLose +
                ", points=" + points +
                '}';
    }
}
