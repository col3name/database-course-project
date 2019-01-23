package com.hockey.infrastructure.repositories;

import com.hockey.Application;
import com.hockey.core.entity.player.PlayerPosition;
import com.hockey.core.exception.EntityException;
import com.hockey.core.interfaces.Specification;
import com.hockey.infrastructure.specifications.FindByIdSpecification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class PlayerPositionRepositoryImpl extends BaseRepository<PlayerPosition> {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public PlayerPositionRepositoryImpl() throws SQLException {
        super();
    }

    @Override
    public List<PlayerPosition> findAll() {
        List<PlayerPosition> playerPositions = new LinkedList<>();

        try {
            String query = "SELECT * FROM " + PlayerPosition.TABLE_NAME + ";";
            ResultSet result = executeQuery(query);
            while (result.next()) {
                PlayerPosition playerPosition = createPlayerPosition(result);
                Long id = result.getLong("id");
                playerPosition.setId(id);
                playerPositions.add(playerPosition);
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return playerPositions;
    }

    @Override
    public PlayerPosition findById(Long id) {
        FindByIdSpecification specification = new FindByIdSpecification();

        try {
            ResultSet result = executeQuery(specification.toSqlQuery());
            if (result.next()) {
                return createPlayerPosition(result);
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return null;
    }

    private PlayerPosition createPlayerPosition(ResultSet result) throws SQLException {
        String fullName = result.getString("full_name");
        String shortName = result.getString("short_name");

        return new PlayerPosition(fullName, shortName);
    }

    @Override
    public boolean create(PlayerPosition item) throws SQLException {
        String sql = "INSERT INTO " + PlayerPosition.TABLE_NAME + " (full_name, short_name) VALUE ('" + item.getFullName() + ", " + item.getShortName() + "');";
        int affectedRows = executeUpdate(sql);

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        PlayerPosition playerPosition = new PlayerPosition(item.getFullName(), item.getShortName());
        playerPosition.setId(getLastInsertedId());
        return true;
    }

    @Override
    public boolean update(PlayerPosition item) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return delete(PlayerPosition.TABLE_NAME, id);
    }

    @Override
    public boolean delete(Specification specification) {
        return false;
    }

    @Override
    public List<PlayerPosition> query(Specification specification) {
        return new ArrayList<>();
    }
}
