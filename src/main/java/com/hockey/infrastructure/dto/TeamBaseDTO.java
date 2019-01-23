package com.hockey.infrastructure.dto;

import com.hockey.core.entity.player.City;

import java.sql.Date;

public class TeamBaseDTO {
    private Long id;
    private String teamName;
    private String avatar;
    private Date foundationDate;
    private City city;

    public TeamBaseDTO() {
    }

    public TeamBaseDTO(Long id,
                       String teamName, String avatar,
                       Date foundationDate,
                       City city) {
        this.id = id;
        this.teamName = teamName;
        this.avatar = avatar;
        this.foundationDate = foundationDate;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(Date foundationDate) {
        this.foundationDate = foundationDate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "TeamBaseDTO{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", foundationDate=" + foundationDate +
                ", city=" + city +
                '}';
    }
}
