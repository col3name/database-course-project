package com.hockey.infrastructure.repositories;

import com.hockey.Application;
import com.hockey.core.entity.league.GameType;
import com.hockey.core.interfaces.services.StandingRepository;
import com.hockey.infrastructure.DBConnectionFactory;
import com.hockey.infrastructure.dto.StandingDTO;
import com.hockey.infrastructure.dto.StandingDTOAssembler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class StandingRepositoryImpl implements StandingRepository {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    private Connection connection;

    public StandingRepositoryImpl() throws SQLException {
        connection = DBConnectionFactory.getConnection();
    }

    @Override
    public List<StandingDTO> getStanding(Long seasonId) {
        List<StandingDTO> standingDTOList = new LinkedList<>();

//       " '2017-10-01' AND s.end <= '2018-05-01'"

        System.out.println(seasonId);

        String teamGuestStats = getTeamStats(seasonId, GameType.GUEST);
        String teamHomeStats = getTeamStats(seasonId, GameType.HOME);

        String sql = "SELECT " +
                "  team_id, " +
                "  team_name, " +
                "  SUM(game_count)                       AS game_count, " +
                "  SUM(goals_scored)                     AS goals_scored, " +
                "  SUM(goals_missed)                     AS goals_missed, " +
                "  SUM(goals_scored) - SUM(goals_missed) AS difference, " +
                "  SUM(is_win)                           AS count_win, " +
                "  SUM(is_lose)                          AS count_lose, " +
                "  SUM(is_overtime_win)                  AS count_overtime_win, " +
                "  SUM(is_overtime_lose)                 AS count_overtime_lose, " +
                "  SUM(point)                            AS points " +
                "FROM ((" + teamGuestStats + ") " +
                "      UNION ALL " +
                "      (" + teamHomeStats + ")) all_game " +
                " GROUP BY team_id " +
                " ORDER BY points DESC;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                standingDTOList.add(StandingDTOAssembler.assembler(resultSet));
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }
        return standingDTOList;
    }

    private String getTeamStats(Long seasonId, GameType gameType) {

        return " SELECT " +
                "         game.id                                                                                AS game_id, " +
                "         COUNT(game.id)                                                                         AS guest_game_count, " +
                "         team.id                                                                                AS team_id, " +
                "         team.name                                                                              AS team_name, " +
                "         COUNT(game.id)                                                                         AS game_count, " +
                "         SUM(game.home_team_goal_count)                                                         AS goals_scored, " +
                "         SUM(game.guest_team_goal_count)                                                        AS goals_missed, " +
                "         SUM(game.guest_team_goal_count > game.home_team_goal_count AND game.time = '01:00:00') AS is_win, " +
                "         SUM(game.guest_team_goal_count < game.home_team_goal_count AND game.time = '01:00:00') AS is_lose, " +
                "         SUM(game.guest_team_goal_count > game.home_team_goal_count AND game.time > '01:00:00') AS is_overtime_win, " +
                "         SUM(game.guest_team_goal_count < game.home_team_goal_count AND game.time > '01:00:00') AS is_overtime_lose, " +
                "         SUM(getGamePoint(guest_team_goal_count, home_team_goal_count, time))                   AS point " +
                "       FROM game " +
                "         LEFT JOIN team ON game." + gameType.getName() + "_team_id = team.id " +
                "         LEFT JOIN season s on game.season_id = s.id " +
                "       WHERE s.id = " + seasonId + " AND game.end IS NOT NULL " +
                "       GROUP BY team_id ";
    }
}
