package com.hockey.core.interfaces.repositories;

import com.hockey.core.entity.league.Game;
import com.hockey.core.entity.league.GameType;
import com.hockey.core.exception.EntityNotFoundException;
import com.hockey.infrastructure.dto.GameDTO;
import com.hockey.infrastructure.dto.PlayerBaseDTO;
import com.hockey.infrastructure.repositories.BaseRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class GameRepository extends BaseRepository<Game> {
    protected GameRepository() throws SQLException {
    }

    /**
     * Получить последние 5 игр каждой из команд до даты начала игры
     * @param Long gameId
     * @return Map<GameType, List<GameDTO>>
     */
    public abstract Map<GameType, List<GameDTO>> getGamesBeforeGameStartGroupByTeam(Long gameId);

    public abstract GameDTO getById(Long gameId) throws SQLException, EntityNotFoundException;

    public abstract Map<GameType, List<PlayerBaseDTO>> getRosterByTeam(Long gameId);
}
