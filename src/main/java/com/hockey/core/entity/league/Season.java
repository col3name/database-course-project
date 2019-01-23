package com.hockey.core.entity.league;

import com.hockey.core.entity.BaseEntity;

import java.sql.Date;

public class Season extends BaseEntity {
    public static final String TABLE_NAME = "season";

    private final Date start;

    private final Date end;

    public Season(Date start, Date end) {
        if (start.compareTo(end) >= 0) {
            throw new IllegalArgumentException("the season must begin date earlier than the end date");
        }

        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Season{" +
                ", id=" + id +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
