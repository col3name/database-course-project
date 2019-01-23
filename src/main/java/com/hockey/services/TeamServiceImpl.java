package com.hockey.services;

import com.hockey.core.entity.league.Team;
import com.hockey.core.interfaces.repositories.TeamRepository;
import com.hockey.core.interfaces.services.TeamService;
import com.hockey.infrastructure.dto.GameDTO;
import com.hockey.infrastructure.dto.TeamBaseDTO;
import com.hockey.infrastructure.dto.TeamRosterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;

    @Autowired
    public TeamServiceImpl(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Team> getAllTeams() {
        return repository.findAll();
    }

    @Override
    public Team getById(Long teamId) throws Exception {
        return repository.findById(teamId);
    }

    @Override
    public TeamBaseDTO getBaseInfo(Long teamId) {
        return repository.getBaseInfo(teamId);
    }

    @Override
    public List<TeamRosterDTO> getRoster(Long teamId) {
        return repository.getTeamRosterOnSeason(teamId);
    }

    @Override
    public List<GameDTO> getGameInPeriod(Long teamId, Integer dayOffset) {
        return repository.getGameInPeriod(teamId, dayOffset);
    }

    @Override
    public List<GameDTO> getTeamLastGames(Long teamId, Integer gameCount) {
        return repository.getLastGames(teamId, gameCount);
    }
}
