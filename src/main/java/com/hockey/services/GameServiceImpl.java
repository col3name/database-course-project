package com.hockey.services;

import com.hockey.core.entity.league.Game;
import com.hockey.core.entity.league.GameType;
import com.hockey.core.interfaces.repositories.GameRepository;
import com.hockey.core.interfaces.services.GameService;
import com.hockey.infrastructure.dto.GameDTO;
import com.hockey.infrastructure.dto.PlayerBaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {
    private GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Map<GameType, List<GameDTO>> getGamesBeforeGameStartGroupByTeam(Long gameId) {
        return gameRepository.getGamesBeforeGameStartGroupByTeam(gameId);
    }

    @Override
    public GameDTO getById(Long gameId) throws Exception {
        return gameRepository.getById(gameId);
    }

    @Override
    public Map<GameType, List<GameDTO>> getStatistic(Long gameId) {
        return Collections.emptyMap();
    }

    @Override
    public Map<GameType, List<PlayerBaseDTO>> getRosterByTeam(Long gameId) {
        return gameRepository.getRosterByTeam(gameId);
    }
}
