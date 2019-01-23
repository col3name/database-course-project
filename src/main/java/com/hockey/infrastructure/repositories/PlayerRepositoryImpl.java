package com.hockey.infrastructure.repositories;

import com.hockey.Application;
import com.hockey.core.entity.league.Team;
import com.hockey.core.entity.player.City;
import com.hockey.core.entity.player.Player;
import com.hockey.core.entity.player.ShootDirection;
import com.hockey.core.exception.InvalidEntityException;
import com.hockey.core.interfaces.Specification;
import com.hockey.core.interfaces.repositories.PlayerRepository;
import com.hockey.infrastructure.DBConnectionFactory;
import com.hockey.infrastructure.dto.PlayerBaseDTO;
import com.hockey.infrastructure.dto.PlayerDetailAssembler;
import com.hockey.infrastructure.dto.PlayerDetailDTO;
import com.hockey.infrastructure.dto.PlayerTeamStandingsDto;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Component("PlayerRepository")
public class PlayerRepositoryImpl extends BaseRepository<Player> implements PlayerRepository {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public PlayerRepositoryImpl() throws SQLException {
        super();
    }

    @Override
    public boolean updatePlayerPosition(Long playerPositionID) {
        return false;
    }

    @Override
    public boolean updateBirthPlace(Long birthPlaceID) {
        return false;
    }

    @Override
    public List<Player> findAll() {
        List<Player> players = new LinkedList<>();

        return players;
    }

    @Override
    public Player findById(Long id) throws Exception {
        String sql = "SELECT " +
                " player.first_name," +
                " player.last_name," +
                " player.born_date," +
                " player.photo," +
                " city.id as birth_place_id," +
                " city.name as birth_place_name," +
                " player_position.id as player_position_id," +
                " player.is_right_shoot " +
                " FROM player " +
                " LEFT JOIN city ON player.id = city.id " +
                " LEFT JOIN player_position ON player.id = player_position.id " +
                " " +
                " WHERE player.id = " + id + ";";
        try {
            Connection connection = DBConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);

            System.out.println(result);
            if (result.next()) {
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                Date bornDate = result.getDate("born_date");
                String photo = result.getString("photo");
                String birthPlaceName = result.getString("birth_place_name");
                Long playerPositionId = result.getLong("player_position_id");
                boolean isRightShoot = result.getBoolean("is_right_shoot");
                City bornPlace = new City(birthPlaceName);

                ShootDirection shootDirection = isRightShoot ? ShootDirection.RIGHT : ShootDirection.LEFT;
                return new Player(firstName, lastName, bornDate, bornPlace, playerPositionId, shootDirection, photo);
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        throw new Exception("Player not found with id '" + id + "'");
    }

    @Override
    public boolean create(Player item) {
        return true;
    }

    @Override
    public List<PlayerBaseDTO> paginate(Integer pageNum, Integer pageSize) {
        System.out.println("paginate");

        System.out.println("pageNum: " + pageNum);
        System.out.println("pageSize: " + pageSize);
        int start = (pageNum - 1) + pageSize;

        String sql = "SELECT " +
                "       player.id AS player_id, " +
                "       player.last_name, " +
                "       player.first_name, " +
                "       player.photo, " +
                "       player_position.full_name, " +
                "       player.born_date " +
                "     FROM player " +
                "            INNER JOIN (" +
                "         SELECT *" +
                "         FROM player" +
                "         ORDER BY player.last_name" +
                "         LIMIT  " + pageNum + ", " + pageSize +
                "       ) AS players USING (id)" +
                "            LEFT JOIN team_roster ON players.id = team_roster.player_id" +
                "            LEFT JOIN season ON team_roster.season_id = season.id" +
                "            LEFT JOIN player_position ON player.player_position_id = player_position.id" +
                "     WHERE (season.id = getSeasonId(CURDATE()))";

        List<PlayerBaseDTO> players = new LinkedList<>();

        try {
            Connection connection = DBConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setInt(1, start);
//            statement.setInt(2, pageSize);
//            ResultSet result = executeQuery(sql);
            ResultSet result = statement.executeQuery(sql);

            System.out.println(result);
            while (result.next()) {
                System.out.println("findasd");

                Long playerId = result.getLong("player_id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String photo = result.getString("photo");
                String playerPositionFullName = result.getString("full_name");
                Date bornDate = result.getDate("born_date");
                players.add(new PlayerBaseDTO(playerId, firstName, lastName, photo, playerPositionFullName, bornDate));
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return players;
    }

    @Override
    public PlayerDetailDTO detail(Long id) throws InvalidEntityException {

        System.out.println("id: " + id);
        try {
            String sql =
                    "SELECT " +
                            "  player.id                                       AS player_id," +
                            "  player.first_name                               AS first_name," +
                            "  player.last_name                                AS last_name," +
                            "  player.born_date                                AS born_date," +
                            "  player.photo," +
                            "  player.is_right_shoot," +
                            "  position.id                                     AS player_position_id," +
                            "  position.full_name                              AS player_position_full_name," +
                            "  city.id                                         AS birth_city_id," +
                            "  city.name                                       AS birth_city_name," +
                            "  GROUP_CONCAT(DISTINCT team.name SEPARATOR ', ') AS player_teams" +
                            " FROM " + Player.TABLE_NAME + "" +
                            "  LEFT JOIN player_position position on player.player_position_id = position.id" +
                            "  LEFT JOIN city ON player.birth_place_id = city.id" +
                            "  LEFT JOIN team_roster ON player.id = team_roster.player_id" +
                            "  LEFT JOIN team ON team_roster.team_id = team.id" +
                            " WHERE player.id = " + id + ";";

            ResultSet result = executeQuery(sql);

            if (result.next()) {
                PlayerDetailAssembler assembler = new PlayerDetailAssembler();

                return assembler.assemblerFromQuery(id, result);
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        throw new InvalidEntityException("Not found");
    }

    @Override
    public List<PlayerTeamStandingsDto> playerTeamStandings(Long playerId) {
        List<PlayerTeamStandingsDto> teamStandings = new LinkedList<>();

        try {
            String sql =
                    "SELECT\n" +
                            "  player.id AS player_id,\n" +
                            "  season.id AS season_id,\n" +
                            "  season.start,\n" +
                            "  season.end,\n" +
                            "  team_id,\n" +
                            "  team_name,\n" +
                            "  game_count,\n" +
                            "  goals_scored,\n" +
                            "  goals_missed,\n" +
                            "  difference,\n" +
                            "  count_win,\n" +
                            "  count_lose,\n" +
                            "  count_overtime_win,\n" +
                            "  count_overtime_lose,\n" +
                            "  points\n" +
                            "FROM player\n" +
                            "  LEFT JOIN team_roster ON player.id = team_roster.player_id\n" +
                            "  lEFT JOIN season ON team_roster.season_id = season.id\n" +
                            "  LEFT JOIN (SELECT\n" +
                            "               season_id,\n" +
                            "               team_name,\n" +
                            "               SUM(game_count)                       AS game_count,\n" +
                            "               SUM(goals_scored)                     AS goals_scored,\n" +
                            "               SUM(goals_missed)                     AS goals_missed,\n" +
                            "               SUM(goals_scored) - SUM(goals_missed) AS difference,\n" +
                            "               SUM(is_win)                           AS count_win,\n" +
                            "               SUM(is_lose)                          AS count_lose,\n" +
                            "               SUM(is_overtime_win)                  AS count_overtime_win,\n" +
                            "               SUM(is_overtime_lose)                 AS count_overtime_lose,\n" +
                            "               SUM(point)                            AS points\n" +
                            "             FROM ((SELECT\n" +
                            "                      game.season_id,\n" +
                            "                      game.id                                                                                AS game_id,\n" +
                            "                      COUNT(\n" +
                            "                          game.id)                                                                           AS guest_game_count,\n" +
                            "                      team.id                                                                                AS team_id,\n" +
                            "                      team.name                                                                              AS team_name,\n" +
                            "                      COUNT(\n" +
                            "                          game.id)                                                                           AS game_count,\n" +
                            "                      SUM(\n" +
                            "                          game.home_team_goal_count)                                                         AS goals_scored,\n" +
                            "                      SUM(\n" +
                            "                          game.guest_team_goal_count)                                                        AS goals_missed,\n" +
                            "                      SUM(game.guest_team_goal_count > game.home_team_goal_count AND game.time = '01:00:00') AS is_win,\n" +
                            "                      SUM(game.guest_team_goal_count < game.home_team_goal_count AND game.time = '01:00:00') AS is_lose,\n" +
                            "                      SUM(game.guest_team_goal_count > game.home_team_goal_count AND game.time >\n" +
                            "                                                                                     '01:00:00')             AS is_overtime_win,\n" +
                            "                      SUM(game.guest_team_goal_count < game.home_team_goal_count AND game.time >\n" +
                            "                                                                                     '01:00:00')             AS is_overtime_lose,\n" +
                            "                      SUM(getGamePoint(guest_team_goal_count, home_team_goal_count, time))                   AS point\n" +
                            "                    FROM game\n" +
                            "                      LEFT JOIN team ON game.guest_team_id = team.id\n" +
                            "                      LEFT JOIN season ON game.season_id = season.id\n" +
                            "                    WHERE game.end IS NOT NULL\n" +
                            "                    GROUP BY season_id)\n" +
                            "                   UNION ALL\n" +
                            "                   (SELECT\n" +
                            "                      game.season_id,\n" +
                            "                      game.id                                                                                AS game_id,\n" +
                            "                      COUNT(\n" +
                            "                          game.id)                                                                           AS home_game_count,\n" +
                            "                      team.id                                                                                AS team_id,\n" +
                            "                      team.name                                                                              AS team_name,\n" +
                            "                      COUNT(\n" +
                            "                          game.id)                                                                           AS game_count,\n" +
                            "                      SUM(\n" +
                            "                          game.home_team_goal_count)                                                         AS goals_scored,\n" +
                            "                      SUM(\n" +
                            "                          game.guest_team_goal_count)                                                        AS goals_missed,\n" +
                            "                      SUM(game.home_team_goal_count > game.guest_team_goal_count AND game.time = '01:00:00') AS is_win,\n" +
                            "                      SUM(game.home_team_goal_count < game.guest_team_goal_count AND game.time = '01:00:00') AS is_lose,\n" +
                            "                      SUM(game.home_team_goal_count > game.guest_team_goal_count AND game.time >\n" +
                            "                                                                                     '01:00:00')             AS is_overtime_win,\n" +
                            "                      SUM(game.home_team_goal_count < game.guest_team_goal_count AND game.time >\n" +
                            "                                                                                     '01:00:00')             AS is_overtime_lose,\n" +
                            "                      SUM(getGamePoint(home_team_goal_count, guest_team_goal_count, time))                   AS point\n" +
                            "                    FROM game\n" +
                            "                      LEFT JOIN team ON game.home_team_id = team.id\n" +
                            "                      LEFT JOIN season ON game.season_id = season.id\n" +
                            "                    WHERE game.end IS NOT NULL\n" +
                            "                    GROUP BY season_id)) all_game\n" +
                            "             GROUP BY season_id\n" +
                            "             ORDER BY points DESC) AS stats ON stats.season_id = season.id\n" +
                            "WHERE player.id = " + playerId + " \n" +
                            "GROUP BY season.id;";

            ResultSet result = executeQuery(sql);

            while (result.next()) {
                Long seasonId = result.getLong("season_id");
                String start = result.getString("start");
                String end = result.getString("end");
                Long teamId = result.getLong("team_id");
                String teamName = result.getString("team_name");
                Integer game_count = result.getInt("game_count");
                Integer goals_scored = result.getInt("goals_scored");
                Integer goals_missed = result.getInt("goals_missed");
                Integer difference = result.getInt("difference");
                Integer count_win = result.getInt("count_win");
                Integer count_lose = result.getInt("count_lose");
                Integer count_overtime_win = result.getInt("count_overtime_win");
                Integer count_overtime_lose = result.getInt("count_overtime_lose");
                Integer points = result.getInt("points");

                Team team = new Team(teamName);
                team.setId(teamId);
                teamStandings.add(new PlayerTeamStandingsDto(playerId, seasonId, start, end, team, game_count, goals_scored,
                        goals_missed, difference, count_win, count_lose, count_overtime_win, count_overtime_lose, points));
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return teamStandings;
    }

    @Override
    public Player create(Player item, Date start, Date end) throws SQLException {
        String sql = "INSERT INTO " + Player.TABLE_NAME + " (first_name, last_name, born_date, birth_place_id, player_position_id, is_right_shoot) VALUE " +
                "('" + item.getFirstName() +
                "','" + item.getLastName() +
                "','" + item.getBornDate() +
                "'," + item.getBornPlace() +
                "," + item.getPlayerPositionId() +
                "," + item.getIsRightShoot() + "');";

        int affectedRows = executeUpdate(sql);

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        return null;
    }

    @Override
    public Player create(PlayerDetailDTO dto) throws SQLException {
        String sql = "INSERT INTO " + Player.TABLE_NAME + " (first_name, last_name, born_date, birth_place_id, player_position_id, is_right_shoot) VALUE " +
                "('" + dto.getFirstName() +
                "','" + dto.getLastName() +
                "','" + dto.getBornDate() +
                "'," + dto.getBirthCityId() +
                "," + dto.getPlayerPositionId() +
                "," + dto.getIsRightShoot() + ");";

        int affectedRows = executeUpdate(sql);

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }
        return null;
    }


    @Override
    public boolean update(Player item) {
        return false;
    }

    @Override
    public boolean update(PlayerDetailDTO dto) {
        try {
            LOG.info(dto.toString());

            int result = executeUpdate("UPDATE " + Player.TABLE_NAME + "\n" +
                    " SET first_name       = '" + dto.getFirstName() + "'," +
                    " last_name         = '" + dto.getLastName() + "'," +
                    " birth_place_id     = " + dto.getBirthCityId() + "," +
                    " player_position_id = " + dto.getPlayerPositionId() + "," +
                    " is_right_shoot     = " + (dto.getIsRightShoot() ? 1 : 0) +
                    " WHERE id = " + dto.getId() + ";");

            return result == 1;
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return delete(Player.TABLE_NAME, id);
    }

    @Override
    public boolean delete(Specification specification) {
        return false;
    }

    @Override
    public List<Player> query(Specification specification) {
        return new ArrayList<>();
    }
}
