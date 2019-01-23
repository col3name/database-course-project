package com.hockey.infrastructure.repositories;

import com.hockey.core.entity.league.Game;
import com.hockey.core.entity.league.GameType;
import com.hockey.core.entity.player.Player;
import com.hockey.core.exception.EntityNotFoundException;
import com.hockey.core.interfaces.Specification;
import com.hockey.core.interfaces.repositories.GameRepository;
import com.hockey.infrastructure.dto.*;
import com.hockey.infrastructure.specifications.GameSpecification;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

@Repository
public class GameRepositoryImpl extends GameRepository {
    private static final Logger LOG = Logger.getLogger(GameRepositoryImpl.class.getName());
    public static final String HOME_TEAM_ID_SQL_VARIABLE = "@home_team_id";
    public static final String GUEST_TEAM_ID_SQL_VARIABLE = "@guest_team_id";
    public static final String GAME_START_SQL_VARIABLE = "@game_start";

    protected GameRepositoryImpl() throws SQLException {
    }

    @Override
    public Map<GameType, List<GameDTO>> getGamesBeforeGameStartGroupByTeam(Long gameId) {
        String sql = "SELECT game.home_team_id, game.guest_team_id, game.start INTO " + HOME_TEAM_ID_SQL_VARIABLE
                + ", " + GUEST_TEAM_ID_SQL_VARIABLE + ", " + GAME_START_SQL_VARIABLE + " " +
                "FROM game " +
                "WHERE id = " + gameId + ";";

        try {
            execute(sql);
            GameSpecification specification = new GameSpecification();

            Map<GameType, List<GameDTO>> gameMapByTeam = new HashMap<>(2);
            gameMapByTeam.put(GameType.HOME, getGames(specification, HOME_TEAM_ID_SQL_VARIABLE));
            gameMapByTeam.put(GameType.GUEST, getGames(specification, GUEST_TEAM_ID_SQL_VARIABLE));
            return gameMapByTeam;
        } catch (SQLException e) {
            LOG.warning(e.getSQLState());
        }

        return Collections.emptyMap();
    }

    @Override
    public GameDTO getById(Long gameId) throws SQLException, EntityNotFoundException {
        return getGameDTO(gameId);
    }

    @Override
    public Map<GameType, List<PlayerBaseDTO>> getRosterByTeam(Long gameId) {
        String sql = "SELECT game.home_team_id, game.guest_team_id, game.start INTO " + HOME_TEAM_ID_SQL_VARIABLE
                + ", " + GUEST_TEAM_ID_SQL_VARIABLE + ", " + GAME_START_SQL_VARIABLE + " " +
                "FROM game " +
                "WHERE id = " + gameId + ";";

        try {
            execute(sql);
            GameSpecification specification = new GameSpecification();

            Map<GameType, List<PlayerBaseDTO>> gameMapByTeam = new HashMap<>(2);
            gameMapByTeam.put(GameType.HOME, getPlayers(specification, HOME_TEAM_ID_SQL_VARIABLE));
//            gameMapByTeam.put(GameType.GUEST, getGames(specification, GUEST_TEAM_ID_SQL_VARIABLE));
            return gameMapByTeam;
        } catch (SQLException e) {
            LOG.warning(e.getSQLState());
        }

        return Collections.emptyMap();

    }

    private GameDTO getGameDTO(Long gameId) throws SQLException, EntityNotFoundException {
        GameSpecification specification = new GameSpecification();
        String sql = "SELECT " +
                specification.gameDto() +
                " FROM game" +
                "  LEFT JOIN team AS home_team ON game.home_team_id = home_team.id " +
                "  LEFT JOIN team AS guest_team ON game.guest_team_id = guest_team.id " +
                "  LEFT JOIN team AS home_team_city ON game.home_team_id = home_team_city.id " +
                "  LEFT JOIN team AS guest_team_city ON game.guest_team_id = guest_team_city.id " +
                " WHERE game.id = " + gameId + ";";

        ResultSet resultSet = executeQuery(sql);
        if (!resultSet.next()) {
            throw new EntityNotFoundException("Unknown game by following id '" + gameId + "'");
        }

        return GameDTOAssembler.assembler(resultSet);
    }

    private List<PlayerBaseDTO> getPlayers(GameSpecification specification, String gameTypeSqlVariable) throws SQLException {
        List<PlayerBaseDTO> players = new ArrayList<>();
//        ResultSet resultSet = executeQuery(specification.gamesBeforeSelectedGame(gameTypeSqlVariable));
//        while (resultSet.next()) {
//            players.add(PlayerBaseDOTAssembler.assembler(resultSet));
//        }

        return players;
    }

    private List<GameDTO> getGames(GameSpecification specification, String gameTypeSqlVariable) throws SQLException {
        List<GameDTO> games = new ArrayList<>();
        ResultSet resultSet = executeQuery(specification.gamesBeforeSelectedGame(gameTypeSqlVariable));
        while (resultSet.next()) {
            games.add(GameDTOAssembler.assembler(resultSet));
        }

        return games;
    }

    @Override
    public List<Game> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Game findById(Long id) throws Exception {
        GameDTO dto = getGameDTO(id);
        Date time = new Date(dto.getEnd().getTime() - dto.getStart().getTime());
        return new Game(dto.getGameId(), dto.getGuestTeamId(), dto.getHomeTeamId(),
                time, dto.getGuestTeamGoalCount(), dto.getHomeTeamGoalCount(), dto.getStart(), dto.getEnd());
    }

    @Override
    public boolean create(Game item) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Game item) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean delete(Specification specification) {
        return false;
    }

    @Override
    public List<Game> query(Specification specification) {
        return null;
    }
}
