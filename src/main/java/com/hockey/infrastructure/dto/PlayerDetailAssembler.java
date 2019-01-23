package com.hockey.infrastructure.dto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class PlayerDetailAssembler {
    public PlayerDetailDTO assemblerFromQuery(Long id, ResultSet requestBody) throws SQLException {
        String firstName = requestBody.getString("first_name");
        String lastName = requestBody.getString("last_name");
        Date bornDate = requestBody.getDate("born_date");
        String photo = requestBody.getString("photo");
        Boolean isRightShoot = requestBody.getBoolean("is_right_shoot");
        Long playerPositionId = requestBody.getLong("player_position_id");
        String playerPositionFullName = requestBody.getString("player_position_full_name");
        Long birthCityId = requestBody.getLong("birth_city_id");
        String birthCityName = requestBody.getString("birth_city_name");
        String playerTeams = requestBody.getString("player_teams");

        return new PlayerDetailDTO(id,
                firstName,
                lastName,
                bornDate,
                photo,
                isRightShoot,
                playerPositionId,
                playerPositionFullName,
                birthCityId,
                birthCityName,
                playerTeams);
    }

    public PlayerDetailDTO assemblerFromMap(Long id, Map<String, String> requestBody) {
        String firstName = requestBody.get("firstName");
        String lastName = requestBody.get("lastName");
        String bornDateStr = requestBody.get("bornDate");
        Date bornDate = Date.valueOf(bornDateStr);
        String photo = requestBody.get("photo");
        Boolean isRightShoot = Boolean.parseBoolean(requestBody.get("isRightShoot"));
        Long playerPositionId = Long.parseLong(requestBody.get("playerPositionId"));
        String playerPositionFullName = requestBody.get("playerPositionFullName");
        Long birthCityId = Long.parseLong(requestBody.get("birthCityId"));
        String birthCityName = requestBody.get("birthCityName");
        String playerTeams = requestBody.get("playerTeams");

        return new PlayerDetailDTO(id,
                firstName,
                lastName,
                bornDate,
                photo,
                isRightShoot,
                playerPositionId,
                playerPositionFullName,
                birthCityId,
                birthCityName,
                playerTeams);
    }
}
