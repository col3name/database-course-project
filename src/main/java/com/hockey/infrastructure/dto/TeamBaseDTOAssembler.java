package com.hockey.infrastructure.dto;

import com.hockey.core.entity.player.City;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamBaseDTOAssembler {
    private TeamBaseDTOAssembler() {
    }

    public static TeamBaseDTO assembler(ResultSet resultSet) throws SQLException {

        Long teamId = resultSet.getLong("team_id");
        String teamName = resultSet.getString("team_name");
        String teamAvatar = resultSet.getString("team_avatar");
        Date foundationDate = resultSet.getDate("foundation_date");
        Long cityId = resultSet.getLong("city_id");
        String cityName = resultSet.getString("city_name");

        City city = new City(cityName);
        city.setId(cityId);

        return new TeamBaseDTO(teamId,
                teamName,
                teamAvatar,
                foundationDate,
                city);
    }
}
