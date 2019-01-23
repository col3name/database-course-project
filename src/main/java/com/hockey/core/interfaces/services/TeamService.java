package com.hockey.core.interfaces.services;

import com.hockey.core.entity.league.Team;
import com.hockey.infrastructure.dto.GameDTO;
import com.hockey.infrastructure.dto.TeamBaseDTO;
import com.hockey.infrastructure.dto.TeamRosterDTO;

import java.util.List;

public interface TeamService {
    List<Team> getAllTeams();

    Team getById(Long teamId) throws Exception;

    TeamBaseDTO getBaseInfo(Long teamId);

//    public void addPlayerToTeam(TeamRosterDTO teamRosterDTO);

    List<TeamRosterDTO> getRoster(Long teamId);

    List<GameDTO> getGameInPeriod(Long teamId, Integer dayOffset);

    List<GameDTO> getTeamLastGames(Long teamId, Integer gameCount);
}
