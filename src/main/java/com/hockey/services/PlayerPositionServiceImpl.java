package com.hockey.services;

import com.hockey.Application;
import com.hockey.core.entity.player.PlayerPosition;
import com.hockey.core.exception.EntityNotFoundException;
import com.hockey.core.exception.InvalidOperationException;
import com.hockey.core.interfaces.repositories.Repository;
import com.hockey.core.interfaces.services.PlayerPositionService;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class PlayerPositionServiceImpl implements PlayerPositionService {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    private final Repository<PlayerPosition> repository;

    public PlayerPositionServiceImpl(Repository<PlayerPosition> repository) {
        this.repository = repository;
    }

    public List<PlayerPosition> findAll() {
        return repository.findAll();
    }

    @Override
    public PlayerPosition findPlayerPositionById(Long id) throws Exception {
        return repository.findById(id);
    }

    @Override
    public boolean createPlayerPosition(String name) throws InvalidOperationException {
        try {
            final PlayerPosition playerPosition = new PlayerPosition(name);
            return repository.create(playerPosition);
        } catch (SQLException e) {
            LOG.warning(e.getSQLState());
            throw new InvalidOperationException("failed create player position with name '" + name + "'");
        }
    }

    @Override
    public boolean updatePlayerPosition(Long id, String name) {
        PlayerPosition item = new PlayerPosition(name);
        item.setId(id);
        return repository.update(item);
    }

    @Override
    public boolean deletePlayerPosition(Long id) {
        return repository.delete(id);
    }
}
