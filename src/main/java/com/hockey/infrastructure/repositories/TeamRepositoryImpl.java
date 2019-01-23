package com.hockey.infrastructure.repositories;

import com.hockey.Application;
import com.hockey.core.entity.league.Team;
import com.hockey.core.exception.EntityNotFoundException;
import com.hockey.core.interfaces.Specification;
import com.hockey.core.interfaces.repositories.TeamRepository;
import com.hockey.infrastructure.DBConnectionFactory;
import com.hockey.infrastructure.dto.*;
import com.hockey.infrastructure.specifications.FindByIdSpecification;
import com.hockey.infrastructure.specifications.GameSpecification;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class TeamRepositoryImpl extends BaseRepository<Team> implements TeamRepository {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public TeamRepositoryImpl() throws SQLException {
        super();
    }

    @Override
    public List<Team> findAll() {
        List<Team> teams = new LinkedList<>();

        try {
            String query = "SELECT * FROM " + Team.TABLE_NAME + ";";
            ResultSet result = executeQuery(query);
            while (result.next()) {
                Team team = getTeam(result);
                teams.add(team);
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return teams;
    }

    private Team getTeam(ResultSet result) throws SQLException {
        Long teamId = result.getLong("id");
        String name = result.getString("name");
        String avatar = result.getString("avatar");
        String foundationDateStr = result.getString("foundation_date");
        Date foundationDate = Date.valueOf(foundationDateStr);

        Team team = new Team(name, avatar, foundationDate);
        team.setId(teamId);
        return team;
    }

    @Override
    public Team findById(Long id) throws EntityNotFoundException {
        FindByIdSpecification specification = new FindByIdSpecification();

        try {
            ResultSet result = executeQuery(specification.toSqlQuery());
            if (!result.next()) {
                throw new EntityNotFoundException("Unknown team");
            }
            return getTeam(result);
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        throw new EntityNotFoundException("Unknown team id");
    }

    @Override
    public boolean create(Team item) {
        String sql = "INSERT INTO " + Team.TABLE_NAME + " (name, avatar, foundation_date) VALUE " +
                "('" + item.getName() +
                "','" + item.getAvatar() +
                "','" + item.getFoundationDate() + "';";

        try {
            int affectedRows = executeUpdate(sql);
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            return true;
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return false;
    }

//
//    public boolean create(TeamBaseDTO dto) {
//        String sql = "INSERT INTO " + Team.TABLE_NAME + " (name, avatar, foundation_date) VALUE " +
//                "('" + dto.getTeamName() +
//                "','" + dto.getAvatar() +
//                "','" + dto.getFoundationDate() + "';";
//
//        try {
//            int affectedRows = executeUpdate(sql);
//            if (affectedRows == 0) {
//                throw new SQLException("Creating user failed, no rows affected.");
//            }
//            return true;
//        } catch (SQLException e) {
//            LOG.warning(e.getMessage());
//        }
//
//        return false;
//    }

    public boolean addPlayerToTeam(TeamRosterDTO playerDTO) {
//        String sql = "INSERT INTO team_roster (season_id, team_id, player_id) VALUE (" + playerDTO.getSeasonId() + ","
//                + playerDTO.getTeamId() + "," +
//                playerDTO.getPlayerId() + ");";
//        try {
//            int i = executeUpdate(sql);
//            if (i == 0) {
//                return false;
//            }
//        } catch (SQLException e) {
//            LOG.warning(e.getMessage());
//            return false;
//        }
        return false;
    }

    @Override
    public TeamBaseDTO getBaseInfo(Long teamId) {
        String sql = "SELECT" +
                "  team.id     AS team_id," +
                "  team.name   AS team_name," +
                "  team.avatar AS team_avatar," +
                "  city.id   AS city_id," +
                "  city.name   AS city_name," +
                "  foundation_date" +
                " FROM  " + Team.TABLE_NAME +
                "  LEFT JOIN team_in_city ON team.id = team_in_city.team_id" +
                "  LEFT JOIN city ON team_in_city.city_id = city.id" +
                " WHERE team.id = " + teamId + " ;";

        try {
            connection = DBConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return TeamBaseDTOAssembler.assembler(resultSet);
            }

        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return new TeamBaseDTO();
    }

    @Override
    public List<TeamRosterDTO> getTeamRosterOnSeason(Long teamId) {
        List<TeamRosterDTO> teamRoster = new LinkedList<>();

        try {
            String sql = "SELECT DISTINCT " +
                    "  player_id, " +
                    "  player.first_name, " +
                    "  player.last_name, " +
                    "  player_position.full_name AS position_name, " +
                    "  player.born_date " +
                    " FROM " + Team.TEAM_ROSTER +
                    "  LEFT JOIN season ON team_roster.season_id = season.id " +
                    "  LEFT JOIN player ON team_roster.player_id = player.id " +
                    "  LEFT JOIN player_position ON player.player_position_id = player_position.id " +
                    " WHERE (season.id = getSeasonId(CURDATE())) AND team_id = " + teamId + ";";

            ResultSet resultSet = executeQuery(sql);

            while (resultSet.next()) {
                TeamRosterDTO teamRosterDTO = TeamRosterDOTAssembler.assembler(resultSet);
                teamRoster.add(teamRosterDTO);
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return teamRoster;
    }

    @Override
    public List<GameDTO> getLastGames(Long teamId, Integer gameCount) {
        GameSpecification specification = new GameSpecification();

        String sql = "SELECT DISTINCT " + specification.gameDto() +
                " FROM game " +
                " INNER JOIN ( " +
                "              SELECT " +
                "                id, " +
                "                home_team_id, " +
                "                guest_team_id " +
                "              FROM game " +
                "              WHERE (end IS NOT NULL) AND (home_team_id = ? OR guest_team_id = ?) " +
                "              ORDER BY start DESC " +
                "              LIMIT ? " +
                "            ) AS last_game_id USING (id) " +

                " LEFT JOIN team AS home_team ON last_game_id.home_team_id = home_team.id " +
                " LEFT JOIN team_in_city AS home_team_in_city ON home_team.id = home_team_in_city.team_id " +
                " LEFT JOIN city AS home_team_city ON home_team_in_city.city_id = home_team_city.id " +

                " LEFT JOIN team AS guest_team ON last_game_id.guest_team_id = guest_team.id " +
                " LEFT JOIN team_in_city AS guest_team_in_city ON guest_team.id = guest_team_in_city.team_id " +
                " LEFT JOIN city AS guest_team_city ON guest_team_in_city.city_id = guest_team_city.id;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, teamId);
            statement.setLong(2, teamId);
            statement.setInt(3, gameCount);

            return getGames(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private List<GameDTO> getGames(PreparedStatement statement) throws SQLException {
        List<GameDTO> games = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            games.add(GameDTOAssembler.assembler(resultSet));
        }

        return games;
    }

    @Override
    public List<GameDTO> getGameInPeriod(Long teamId, Integer dayOffset) {
        GameSpecification specification = new GameSpecification();

        String sql = "SELECT " +
                specification.gameDto() +
                " FROM game " +
                "  LEFT JOIN team AS home_team ON game.home_team_id = home_team.id " +
                "  LEFT JOIN team AS guest_team ON game.guest_team_id = guest_team.id " +
                "  LEFT JOIN team AS home_team_city ON game.home_team_id = home_team_city.id " +
                "  LEFT JOIN team AS guest_team_city ON game.guest_team_id = guest_team_city.id " +
                " WHERE (home_team.id = ? OR guest_team.id = ?) AND game.season_id = getSeasonId(CURDATE()) " +
                "      AND (game.start >= DATE_SUB(CURDATE(), INTERVAL ? DAY) AND game.start <= DATE_ADD(CURDATE(), INTERVAL ? DAY));";

        System.out.println(sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, teamId);
            statement.setLong(2, teamId);
            statement.setInt(3, dayOffset);
            statement.setInt(4, dayOffset);

            return getGames(statement);

        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return Collections.emptyList();
    }

    @Override
    public boolean update(Team item) {
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
    public List<Team> query(Specification specification) {
        return new ArrayList<>();
    }
}
