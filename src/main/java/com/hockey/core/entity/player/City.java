package com.hockey.core.entity.player;

import com.hockey.core.entity.BaseEntity;

public class City extends BaseEntity {
    public static final String TABLE_NAME = "city";

    private String name;

    public City() {
        super();
    }

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}