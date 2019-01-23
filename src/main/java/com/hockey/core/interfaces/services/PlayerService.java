package com.hockey.core.interfaces.services;

import com.hockey.core.entity.player.Player;
import com.hockey.core.exception.InvalidEntityException;
import com.hockey.infrastructure.dto.PlayerBaseDTO;
import com.hockey.infrastructure.dto.PlayerDetailDTO;
import com.hockey.infrastructure.dto.PlayerTeamStandingsDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface PlayerService {
    void save(Player player);

    Player findById(Long id) throws Exception;

    PlayerDetailDTO getDetailPlayerInfo(Long id) throws InvalidEntityException;

    Player createPlayer(Map<String, String> body) throws SQLException;

    boolean updatePlayer(Long playerID, Map<String, String> body);

    boolean deletePlayer(Long playerId);

    List<PlayerBaseDTO> paginate(Integer page, Integer size);

    List<PlayerTeamStandingsDto> playerTeamStandings(Long playerId);
}
