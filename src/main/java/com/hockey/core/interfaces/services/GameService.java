package com.hockey.core.interfaces.services;

import com.hockey.core.entity.league.GameType;
import com.hockey.infrastructure.dto.GameDTO;
import com.hockey.infrastructure.dto.PlayerBaseDTO;

import java.util.List;
import java.util.Map;

public interface GameService {
    Map<GameType, List<GameDTO>> getGamesBeforeGameStartGroupByTeam(Long gameId);

    GameDTO getById(Long gameId) throws Exception;

    Map<GameType, List<GameDTO>> getStatistic(Long gameId);

    Map<GameType, List<PlayerBaseDTO>> getRosterByTeam(Long gameId);
}
