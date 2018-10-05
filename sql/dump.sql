CREATE DATABASE IF NOT EXISTS hockey_protocol;
USE hockey_protocol;

CREATE TABLE IF NOT EXISTS role (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),

  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE UNIQUE INDEX UN_role_id
  ON `role` (id);
CREATE INDEX IN_role_name
  ON `role` (name);

CREATE TABLE IF NOT EXISTS user (
  id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(255),
  email VARCHAR(254),
  password VARCHAR(255),
  avatar VARCHAR(255),

  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE UNIQUE INDEX UN_user_id
  ON `user` (id);
CREATE FULLTEXT INDEX IN_user_username
  ON `user` (username);
CREATE FULLTEXT INDEX IN_user_email
  ON `user` (email);
CREATE INDEX UN_user_password
  ON `user` (password);
CREATE INDEX UN_user_avatar
  ON `user` (avatar);

CREATE TABLE IF NOT EXISTS user_role (
  user_id INT(11) NOT NULL,
  role_id INT(11) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (role_id) REFERENCES role(id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE INDEX UN_user_role_user_id
  ON `user_role` (user_id);
CREATE INDEX IN_user_role_role_id
  ON `user_role` (role_id);


CREATE TABLE IF NOT EXISTS player_position (
  id INT(11) NOT NULL AUTO_INCREMENT,
  full_name VARCHAR(255),
  short_name VARCHAR(255),

  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE UNIQUE INDEX UN_player_position_id
  ON `player_position` (id);
CREATE INDEX IN_player_position_full_name
  ON `player_position` (full_name);
CREATE INDEX IN_player_position_short_name
  ON `player_position` (short_name);

CREATE TABLE IF NOT EXISTS city (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),

  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE UNIQUE INDEX UN_city_id
  ON `city` (id);
CREATE INDEX IN_city_name
  ON `city` (name);

CREATE TABLE IF NOT EXISTS player (
  id INT(11) NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255),
  last_name VARCHAR(254),
  born_date DATE,
  photo VARCHAR(255),
  birth_place_id INT(11),
  player_position_id INT(11),
  is_right_shoot TINYINT(1),

  PRIMARY KEY (id),
  FOREIGN KEY (player_position_id) REFERENCES player_position(id) ON DELETE CASCADE,
  FOREIGN KEY (birth_place_id) REFERENCES city(id) ON DELETE CASCADE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE UNIQUE INDEX UN_player_id
  ON `player` (id);
CREATE FULLTEXT INDEX FN_player_first_name
  ON `player` (first_name);
CREATE FULLTEXT INDEX FN_player_last_name
  ON `player` (last_name);
CREATE INDEX FN_player_born_date
  ON `player` (born_date);
CREATE INDEX IN_player_photo
  ON `player` (photo);
CREATE INDEX IN_player_birth_place_id
  ON `player` (birth_place_id);
CREATE INDEX IN_player_player_position_id
  ON `player` (player_position_id);
CREATE INDEX IN_player_player_is_right_shoot
  ON `player` (is_right_shoot);


CREATE TABLE IF NOT EXISTS team (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  avatar VARCHAR(255),
  foundation_date DATE,

  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE UNIQUE INDEX UN_team_id
  ON `team` (id);
CREATE FULLTEXT INDEX FN_team_name
  ON `team` (name);
CREATE INDEX IN_team_avatar
  ON `team` (avatar);
CREATE INDEX IN_team_foundation_date
  ON `team` (foundation_date);

CREATE TABLE IF NOT EXISTS team_in_city (
  team_id INT(11) NOT NULL,
  city_id INT(11) NOT NULL,
  FOREIGN KEY (team_id) REFERENCES team(id),
  FOREIGN KEY (city_id) REFERENCES city(id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE INDEX UN_team_in_city_team_id
  ON `team_in_city` (team_id);
CREATE INDEX IN_team_in_city_city_id
  ON `team_in_city` (city_id);


CREATE TABLE IF NOT EXISTS season (
  id INT(11) NOT NULL AUTO_INCREMENT,
  start DATE,
  end DATE,

  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE UNIQUE INDEX UN_season_id
  ON `season` (id);
CREATE INDEX IN_season_start
  ON `season` (start);
CREATE INDEX IN_season_end
  ON `season` (end);

CREATE TABLE IF NOT EXISTS team_roster (
  season_id INT(11) NOT NULL,
  team_id INT(11) NOT NULL,
  player_id INT(11) NOT NULL,
  FOREIGN KEY (season_id) REFERENCES season(id),
  FOREIGN KEY (team_id) REFERENCES team(id),
  FOREIGN KEY (player_id) REFERENCES player(id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

# игрок не может быть в ростере команды
CREATE INDEX UN_team_roster_season__id_team_id__player_id
  ON `team_roster` (season_id, team_id, player_id);

CREATE INDEX UN_team_roster_season__id__player_id
  ON `team_roster` (season_id, player_id);

CREATE INDEX IN_team_roster_season_id
  ON `team_roster` (season_id);
CREATE INDEX IN_team_roster_team_id
  ON `team_roster` (team_id);
CREATE INDEX IN_team_roster_player_id
  ON `team_roster` (player_id);

CREATE TABLE IF NOT EXISTS game (
  id INT(11) NOT NULL AUTO_INCREMENT,
  season_id INT(11) NOT NULL,
  guest_team_id INT(11) NOT NULL,
  home_team_id INT(11) NOT NULL,
  time TIME,
  guest_team_goal_count INT(11) DEFAULT 0,
  home_team_goal_count INT(11) DEFAULT 0,
  start DATETIME,
  end DATETIME,

  PRIMARY KEY (id),
  FOREIGN KEY (season_id) REFERENCES season(id),
  FOREIGN KEY (guest_team_id) REFERENCES player(id),
  FOREIGN KEY (home_team_id) REFERENCES player(id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE UNIQUE INDEX UN_game_id
  ON `game` (id);
CREATE INDEX IN_game_season_id
  ON `game` (season_id);
CREATE INDEX IN_game_guest_team_id
  ON `game` (guest_team_id);
CREATE INDEX IN_game_guest_home_team_id
  ON `game` (home_team_id);
CREATE INDEX IN_game_time
  ON `game` (time);
CREATE INDEX IN_game_guest_team_goal_count
  ON `game` (guest_team_goal_count);
CREATE INDEX IN_game_home_team_goal_count
  ON `game` (home_team_goal_count);
CREATE INDEX IN_game_start
  ON `game` (start);
CREATE INDEX IN_game_end
  ON `game` (end);

# командна может одновременно участвовать только в 1 игре
CREATE UNIQUE INDEX UN_game_home_team_start
  ON `game` (home_team_id, start);
CREATE UNIQUE INDEX UN_game_guest_team_start
  ON `game` (guest_team_id, start);