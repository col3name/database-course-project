package com.hockey.services;

import com.hockey.Application;
import com.hockey.core.entity.player.Player;
import com.hockey.core.exception.InvalidEntityException;
import com.hockey.core.interfaces.repositories.PlayerRepository;
import com.hockey.core.interfaces.services.PlayerService;
import com.hockey.infrastructure.dto.PlayerBaseDTO;
import com.hockey.infrastructure.dto.PlayerDetailAssembler;
import com.hockey.infrastructure.dto.PlayerDetailDTO;
import com.hockey.infrastructure.dto.PlayerTeamStandingsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component("PlayerService")
public class PlayerServiceImpl implements PlayerService {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    private final PlayerRepository repository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Player player) {
        try {
            repository.create(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Player findById(Long id) throws Exception {
        return repository.findById(id);
    }

    @Override
    public PlayerDetailDTO getDetailPlayerInfo(Long id) throws InvalidEntityException {
        return repository.detail(id);
    }

    @Override
    public Player createPlayer(Map<String, String> body) throws SQLException {
        PlayerDetailAssembler assembler = new PlayerDetailAssembler();

        PlayerDetailDTO dto = assembler.assemblerFromMap(0L, body);
        String msg = dto.toString();
        LOG.info(msg);
        return repository.create(dto);
    }

    @Override
    public boolean updatePlayer(Long playerID, Map<String, String> body) {
        PlayerDetailAssembler assembler = new PlayerDetailAssembler();

        return repository.update(assembler.assemblerFromMap(playerID, body));
    }

    @Override
    public boolean deletePlayer(Long playerId) {
        return repository.delete(playerId);
    }

    @Override
    public List<PlayerBaseDTO> paginate(Integer page, Integer size) {
        return repository.paginate(page, size);
    }

    @Override
    public List<PlayerTeamStandingsDto> playerTeamStandings(Long playerId) {
        return repository.playerTeamStandings(playerId);
    }
}
