package com.hockey.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.hockey.application.config.Constants.*;

public class DBConnectionFactory {

    private static Connection connection  = null;

    private DBConnectionFactory() {
    }
//    private static Connection connection;

    public static Connection getConnection() throws SQLException {
//               if (connection == null) {
//            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
//        }
//        return connection;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException ex) {
            if (connection != null) {
                connection.close();
            }
            throw new SQLException(ex.getMessage(), ex);
        }
        return connection;
    }
}
