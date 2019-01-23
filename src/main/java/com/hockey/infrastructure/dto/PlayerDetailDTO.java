package com.hockey.infrastructure.dto;

import java.sql.Date;

public class PlayerDetailDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Date bornDate;
    private String photo;
    private Boolean isRightShoot;
    private Long playerPositionId;
    private String playerPositionFullName;
    private Long birthCityId;
    private String birthCityName;
    private String playerTeamName;

    public PlayerDetailDTO(Long id,
                           String firstName,
                           String lastName,
                           Date bornDate,
                           String photo,
                           Boolean isRightShoot,
                           Long playerPositionId,
                           String playerPositionFullName,
                           Long birthCityId,
                           String birthCityName,
                           String playerTeams) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bornDate = bornDate;
        this.photo = photo;
        this.isRightShoot = isRightShoot;
        this.playerPositionId = playerPositionId;
        this.playerPositionFullName = playerPositionFullName;
        this.birthCityId = birthCityId;
        this.birthCityName = birthCityName;
        this.playerTeamName = playerTeams;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getIsRightShoot() {
        return isRightShoot;
    }

    public void setRightShoot(Boolean rightShoot) {
        isRightShoot = rightShoot;
    }

    public Long getPlayerPositionId() {
        return playerPositionId;
    }

    public void setPlayerPositionId(Long playerPositionId) {
        this.playerPositionId = playerPositionId;
    }

    public String getPlayerPositionFullName() {
        return playerPositionFullName;
    }

    public void setPlayerPositionFullName(String playerPositionFullName) {
        this.playerPositionFullName = playerPositionFullName;
    }

    public Long getBirthCityId() {
        return birthCityId;
    }

    public void setBirthCityId(Long birthCityId) {
        this.birthCityId = birthCityId;
    }

    public String getBirthCityName() {
        return birthCityName;
    }

    public void setBirthCityName(String birthCityName) {
        this.birthCityName = birthCityName;
    }

    public String getPlayerTeamName() {
        return playerTeamName;
    }

    public void setPlayerTeamName(String playerTeamName) {
        this.playerTeamName = playerTeamName;
    }

    @Override
    public String toString() {
        return "PlayerDetailDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bornDate=" + bornDate +
                ", photo='" + photo + '\'' +
                ", isRightShoot=" + isRightShoot +
                ", playerPositionId=" + playerPositionId +
                ", playerPositionFullName='" + playerPositionFullName + '\'' +
                ", birthCityId=" + birthCityId +
                ", birthCityName='" + birthCityName + '\'' +
                ", playerTeamName='" + playerTeamName + '\'' +
                '}';
    }
}
