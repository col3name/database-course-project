package com.hockey.core.entity.league;

import com.hockey.core.entity.BaseEntity;

import javax.validation.constraints.NotNull;
import java.sql.Date;

public class Team extends BaseEntity {
    public static final String TABLE_NAME = "team";
    public static final String TEAM_ROSTER = "team_roster";

    private String name;

    private String avatar = "";

    private Date foundationDate = new Date(System.currentTimeMillis());

    public Team(String name) {
        this.name = name;
    }

    public Team(String name, String avatar, Date foundationDate) {
        this.name = name;
        this.avatar = avatar;
        this.foundationDate = foundationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
