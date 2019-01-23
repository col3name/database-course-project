package com.hockey.infrastructure.dto;

import java.sql.Date;

public class TeamRosterDTO {
    private Long playerId;
    private String firstName;
    private String lastName;
    private String positionName;
    private Date bornDate;

    public TeamRosterDTO(Long playerId, String firstName, String lastName, String positionName, Date bornDate) {
        this.playerId = playerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.positionName = positionName;
        this.bornDate = bornDate;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    @Override
    public String toString() {
        return "TeamRosterDTO{" +
                "playerId=" + playerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", positionName='" + positionName + '\'' +
                ", bornDate='" + bornDate + '\'' +
                '}';
    }
}
