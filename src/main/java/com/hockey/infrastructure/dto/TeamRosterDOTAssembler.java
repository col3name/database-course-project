package com.hockey.infrastructure.dto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamRosterDOTAssembler {
    private TeamRosterDOTAssembler() {
    }

    public static TeamRosterDTO assembler(ResultSet resultSet) throws SQLException {
        Long playerId = resultSet.getLong("player_id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String positionName = resultSet.getString("position_name");
        Date bornDate = resultSet.getDate("born_date");

        return new TeamRosterDTO(playerId,
                firstName,
                lastName,
                positionName,
                bornDate);
    }
}
