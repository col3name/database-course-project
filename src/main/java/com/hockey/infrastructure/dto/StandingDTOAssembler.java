package com.hockey.infrastructure.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StandingDTOAssembler {
    private StandingDTOAssembler() {
    }

    public static StandingDTO assembler(ResultSet resultSet) throws SQLException {
        Long teamId = resultSet.getLong("team_id");
        String teamName = resultSet.getString("team_name");
        Integer gameCount = resultSet.getInt("game_count");
        Integer goalsScored = resultSet.getInt("goals_scored");
        Integer goalsMissed = resultSet.getInt("goals_missed");
        Integer countWin = resultSet.getInt("count_win");
        Integer countLose = resultSet.getInt("count_lose");
        Integer countOvertimeWin = resultSet.getInt("count_overtime_win");
        Integer countOvertimeLose = resultSet.getInt("count_overtime_lose");
        Integer points = resultSet.getInt("points");

        return new StandingDTO(teamId,
                teamName,
                gameCount,
                goalsScored,
                goalsMissed,
                countWin,
                countLose,
                countOvertimeWin,
                countOvertimeLose,
                points);
    }
}
