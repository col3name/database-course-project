package com.hockey.core.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public abstract class BaseEntity implements TableName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected final String tableName;

    protected BaseEntity() {
        this.tableName = getClass().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTableName() {
        return tableName;
    }
}
