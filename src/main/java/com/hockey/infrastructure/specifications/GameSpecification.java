package com.hockey.infrastructure.specifications;

public class GameSpecification {
    public String gameDto() {
        return "  game.id              AS game_id," +
                "  home_team.id         AS home_team_id," +
                "  home_team.name       AS home_team_name," +
                "  guest_team.id        AS guest_team_id," +
                "  guest_team.name      AS guest_team_name," +
                "  home_team_goal_count," +
                "  guest_team_goal_count," +
                "  guest_team_city.name AS guest_team_city_name," +
                "  home_team_city.name  AS home_team_city_name," +
                "  game.start," +
                "  game.end";
    }

    public String gamesBeforeSelectedGame(String teamTypeIdSqlVariable) {
        return "SELECT " +
                gameDto() +
                " FROM game " +
                "       INNER JOIN ( " +
                "    SELECT game.id " +
                "    FROM game " +
                "    WHERE (home_team_id = " + teamTypeIdSqlVariable + " OR guest_team_id = " + teamTypeIdSqlVariable + ") " +
                "      AND (game.end < @game_start) " +
                "    ORDER BY game.end DESC " +
                "    LIMIT 5 " +
                "  ) AS lim USING (id) " +
                "       LEFT JOIN team AS home_team ON game.home_team_id = home_team.id " +
                "       LEFT JOIN team AS guest_team ON game.guest_team_id = guest_team.id " +
                "       LEFT JOIN team AS home_team_city ON game.home_team_id = home_team_city.id " +
                "       LEFT JOIN team AS guest_team_city ON game.guest_team_id = guest_team_city.id " +
                "ORDER BY game.end DESC;";
    }
}
