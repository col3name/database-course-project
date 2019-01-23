package com.hockey.core.entity.player;

import com.hockey.core.entity.BaseEntity;

import java.util.Date;

public class Player extends BaseEntity {
    public static final String TABLE_NAME = "player";

    private String firstName = "";

    private String lastName = "";

    private Long playerPositionId = 0L;

    private Date bornDate;

    private City bornPlace;

    private ShootDirection isRightShoot;

    private String photoPath;

    public Player() {
    }

    public Player(String firstName, String lastName, Long playerPositionId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.playerPositionId = playerPositionId;
    }

    public Player(String firstName, String lastName, Date bornDate, City bornPlace, Long playerPosition, ShootDirection isRightShoot) {
        this(firstName, lastName, playerPosition);
        this.bornDate = bornDate;
        this.bornPlace = bornPlace;
        this.isRightShoot = isRightShoot;
    }

    public Player(String firstName, String lastName, Date bornDate, City bornPlace, Long playerPosition, ShootDirection isRightShoot, String photoPath) {
        this(firstName, lastName, bornDate, bornPlace, playerPosition, isRightShoot);
        this.photoPath = photoPath;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public City getBornPlace() {
        return bornPlace;
    }

    public Long getPlayerPositionId() {
        return playerPositionId;
    }

    public ShootDirection getIsRightShoot() {
        return isRightShoot;
    }

    public String getPhotoPath() {
        return photoPath;
    }
}
