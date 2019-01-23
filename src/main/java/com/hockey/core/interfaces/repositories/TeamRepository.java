package com.hockey.core.interfaces.repositories;

import com.hockey.core.entity.league.Team;
import com.hockey.infrastructure.dto.GameDTO;
import com.hockey.infrastructure.dto.TeamBaseDTO;
import com.hockey.infrastructure.dto.TeamRosterDTO;

import java.util.List;

public interface TeamRepository extends Repository<Team> {
    TeamBaseDTO getBaseInfo(Long teamId);

    List<TeamRosterDTO> getTeamRosterOnSeason(Long teamId);

    List<GameDTO> getLastGames(Long teamId, Integer gameCount);

    /**
     * [currentDate - countDayOffset; currentDate + countDayOffset]
     */
    List<GameDTO> getGameInPeriod(Long teamId, Integer dayOffset);
}
