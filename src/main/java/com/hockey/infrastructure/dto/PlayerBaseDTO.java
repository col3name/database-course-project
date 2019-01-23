package com.hockey.infrastructure.dto;

import java.sql.Date;

public class PlayerBaseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String photo;
    private String playerPositionFullName;
    private Date bornDate;

    public PlayerBaseDTO(Long id, String firstName, String lastName, String photo, String playerPositionFullName, Date bornDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.playerPositionFullName = playerPositionFullName;
        this.bornDate = bornDate;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPlayerPositionFullName() {
        return playerPositionFullName;
    }

    public void setPlayerPositionFullName(String playerPositionFullName) {
        this.playerPositionFullName = playerPositionFullName;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    @Override
    public String toString() {
        return "PlayerBaseDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", photo='" + photo + '\'' +
                ", playerPositionFullName='" + playerPositionFullName + '\'' +
                ", bornDate=" + bornDate +
                '}';
    }
}
