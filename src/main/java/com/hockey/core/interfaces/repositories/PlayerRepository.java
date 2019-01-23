package com.hockey.core.interfaces.repositories;

import com.hockey.core.entity.player.Player;
import com.hockey.core.exception.InvalidEntityException;
import com.hockey.infrastructure.dto.PlayerBaseDTO;
import com.hockey.infrastructure.dto.PlayerDetailDTO;
import com.hockey.infrastructure.dto.PlayerTeamStandingsDto;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface PlayerRepository extends Repository<Player> {
    List<PlayerBaseDTO> paginate(Integer page, Integer offset);

    PlayerDetailDTO detail(Long id) throws InvalidEntityException;

    Player create(Player item, Date start, Date end) throws SQLException;

    Player create(PlayerDetailDTO dto) throws SQLException;

    boolean updatePlayerPosition(Long playerPositionID);

    boolean updateBirthPlace(Long birthPlaceID);

    boolean update(PlayerDetailDTO dto);

    List<PlayerTeamStandingsDto> playerTeamStandings(Long playerId);
}
