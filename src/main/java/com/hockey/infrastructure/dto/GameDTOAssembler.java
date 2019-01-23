package com.hockey.infrastructure.dto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDTOAssembler {
    private GameDTOAssembler() {
    }

    public static GameDTO assembler(ResultSet resultSet) throws SQLException {
        Long gameId = resultSet.getLong("game_id");
        Long homeTeamId = resultSet.getLong("home_team_id");
        String homeTeamName = resultSet.getString("home_team_name");
        Long guestTeamId = resultSet.getLong("guest_team_id");
        String guestTeamName = resultSet.getString("guest_team_name");
        String homeTeamCityName = resultSet.getString("home_team_city_name");
        String guestTeamCityName = resultSet.getString("guest_team_city_name");
        Integer homeTeamGoalCount = resultSet.getInt("home_team_goal_count");
        Integer guestTeamGoalCount = resultSet.getInt("guest_team_goal_count");
        Date start = resultSet.getDate("start");
        Date end = resultSet.getDate("end");

        return new GameDTO(gameId,
                homeTeamId,
                homeTeamName,
                guestTeamId,
                guestTeamName,
                homeTeamGoalCount,
                guestTeamGoalCount,
                homeTeamCityName,
                guestTeamCityName,
                start,
                end);
    }
}
