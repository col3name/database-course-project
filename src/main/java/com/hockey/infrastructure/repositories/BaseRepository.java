package com.hockey.infrastructure.repositories;

import com.hockey.Application;
import com.hockey.core.entity.BaseEntity;
import com.hockey.core.interfaces.repositories.Repository;
import com.hockey.infrastructure.DBConnectionFactory;

import java.sql.*;
import java.util.logging.Logger;

public abstract class BaseRepository<T extends BaseEntity> implements Repository<T> {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());
    
    protected Connection connection = DBConnectionFactory.getConnection();
    protected PreparedStatement statement;

    protected BaseRepository() throws SQLException {
    }

    protected Long getLastInsertedId(PreparedStatement statement) throws SQLException {
        ResultSet rs = statement.getGeneratedKeys();
        return rs.next() ? rs.getLong(1) : 0;
    }

    protected Long getLastInsertedId() throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    protected boolean execute(String sql) throws SQLException {
        return connection.prepareStatement(sql).execute();
    }

    protected ResultSet executeQuery(String sql) throws SQLException {
        statement = connection.prepareStatement(sql);

        return statement.executeQuery(sql);
    }

    protected int executeUpdate(String sql) throws SQLException {
        return statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
    }

    protected boolean delete(String tableName, Long id) {
        try {
            int result = executeUpdate("DELETE FROM " + tableName + " WHERE id = " + id + ";");
            return result == 1;
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }
        return false;
    }
}
