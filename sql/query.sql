# Standings on season 2017/2018 (game count, win, lose, overtime, points, goals scored, goals missed)
#
DROP FUNCTION IF EXISTS getGamePoint;

DELIMITER //
CREATE FUNCTION getGamePoint(guest_goal_count INT(11), home_goal_count INT(11), time TIME)
  RETURNS INT(11) DETERMINISTIC
  BEGIN
    DECLARE point INT(11);
    IF (guest_goal_count > home_goal_count)
    THEN
      SET point = 2;
    ELSEIF (guest_goal_count <= home_goal_count AND time > '01:00:00')
      THEN
        SET point = 1;
    ELSE
      SET point = 0;
    END IF;
    RETURN (point);
  END //
DELIMITER ;

# 1) Получить таблицу результатов
SELECT
  team_id,
  team_name,
  SUM(game_count)                       AS game_count,
  SUM(goals_scored)                     AS goals_scored,
  SUM(goals_missed)                     AS goals_missed,
  SUM(goals_scored) - SUM(goals_missed) AS difference,
  SUM(is_win)                           AS count_win,
  SUM(is_lose)                          AS count_lose,
  SUM(is_overtime_win)                  AS count_overtime_win,
  SUM(is_overtime_lose)                 AS count_overtime_lose,
  SUM(point)                            AS points
FROM ((SELECT
         game.id                                                                                AS game_id,
         COUNT(game.id)                                                                         AS guest_game_count,
         team.id                                                                                AS team_id,
         team.name                                                                              AS team_name,
         COUNT(game.id)                                                                         AS game_count,
         SUM(game.home_team_goal_count)                                                         AS goals_scored,
         SUM(game.guest_team_goal_count)                                                        AS goals_missed,
         SUM(game.guest_team_goal_count > game.home_team_goal_count AND game.time = '01:00:00') AS is_win,
         SUM(game.guest_team_goal_count < game.home_team_goal_count AND game.time = '01:00:00') AS is_lose,
         SUM(game.guest_team_goal_count > game.home_team_goal_count AND game.time > '01:00:00') AS is_overtime_win,
         SUM(game.guest_team_goal_count < game.home_team_goal_count AND game.time > '01:00:00') AS is_overtime_lose,
         SUM(getGamePoint(guest_team_goal_count, home_team_goal_count, time))                   AS point
       FROM game
         LEFT JOIN team ON game.guest_team_id = team.id
         LEFT JOIN season s on game.season_id = s.id
       WHERE s.start >= '2017-10-01' AND s.end <= '2018-05-01' AND game.end IS NOT NULL
       GROUP BY team_id)
      UNION ALL
      (SELECT
         game.id                                                                                AS game_id,
         COUNT(game.id)                                                                         AS home_game_count,
         team.id                                                                                AS team_id,
         team.name                                                                              AS team_name,
         COUNT(game.id)                                                                         AS game_count,
         SUM(game.home_team_goal_count)                                                         AS goals_scored,
         SUM(game.guest_team_goal_count)                                                        AS goals_missed,
         SUM(game.home_team_goal_count > game.guest_team_goal_count AND game.time = '01:00:00') AS is_win,
         SUM(game.home_team_goal_count < game.guest_team_goal_count AND game.time = '01:00:00') AS is_lose,
         SUM(game.home_team_goal_count > game.guest_team_goal_count AND game.time > '01:00:00') AS is_overtime_win,
         SUM(game.home_team_goal_count < game.guest_team_goal_count AND game.time > '01:00:00') AS is_overtime_lose,
         SUM(getGamePoint(home_team_goal_count, guest_team_goal_count, time))                   AS point
       FROM game
         LEFT JOIN team ON game.home_team_id = team.id
         LEFT JOIN season s on game.season_id = s.id
       WHERE s.start >= '2017-10-01' AND s.end <= '2018-05-01' AND game.end IS NOT NULL
       GROUP BY team_id)) all_game
GROUP BY team_id
ORDER BY points DESC;

# /teams
# Get all team
# 2)
SELECT
  team.id     AS team_id,
  team.name   AS team_name,
  team.avatar As team_avatar,
  city.name   AS city_name
FROM team
  LEFT JOIN team_in_city ON team.id = team_in_city.team_id
  LEFT JOIN city ON team_in_city.city_id = city.id;

# /teams/{team_name}
# Получить основную инофрмацию о клубе
# 3)
SELECT
  team.id     AS team_id,
  team.name   AS team_name,
  team.avatar AS team_avatar,
  city.name   AS city_name,
  foundation_date
FROM team
  LEFT JOIN team_in_city ON team.id = team_in_city.team_id
  LEFT JOIN city ON team_in_city.city_id = city.id;

# Получить результат последних 5 игр
# 4)
SELECT DISTINCT
  game.id              AS game_id,
  home_team.id         AS home_team_id,
  guest_team.name      AS guest_team_name,
  guest_team.id        AS guest_team_id,
  home_team.name       AS home_team_name,
  home_team_goal_count,
  guest_team_goal_count,
  guest_team_city.name AS guest_team_city_name,
  home_team_city.name  AS home_team_city_name,
  game.start,
  game.end
FROM game
  INNER JOIN (
               SELECT
                 id,
                 home_team_id,
                 guest_team_id
               FROM game
               WHERE (end IS NOT NULL) AND (home_team_id = 1 OR guest_team_id = 1)
               ORDER BY start DESC
               LIMIT 5
             ) AS last_game_id USING (id)

  LEFT JOIN team AS home_team ON last_game_id.home_team_id = home_team.id
  LEFT JOIN team_in_city AS home_team_in_city ON home_team.id = home_team_in_city.team_id
  LEFT JOIN city AS home_team_city ON home_team_in_city.city_id = home_team_city.id

  LEFT JOIN team AS guest_team ON last_game_id.guest_team_id = guest_team.id
  LEFT JOIN team_in_city AS guest_team_in_city ON guest_team.id = guest_team_in_city.team_id
  LEFT JOIN city AS guest_team_city ON guest_team_in_city.city_id = guest_team_city.id;

# /teams/{team_name}/players
# Получить состав на текущий сезон (2018/2019) сезон
# 5)
SELECT
  player_id,
  player.first_name,
  player.last_name,
  player_position.full_name,
  player.born_date
FROM team
  LEFT JOIN team_roster ON team.id = team_roster.team_id
  LEFT JOIN season ON team_roster.season_id = season.id
  LEFT JOIN player ON team_roster.player_id = player.id
  LEFT JOIN player_position ON player.player_position_id = player_position.id
WHERE (season.start >= '2018-10-02' AND season.end <= '2019-05-01') AND team_id = 1;

# /players/
# Получить информацию о 30 игроках игроках всех команд в алвафитном порядке на текущий момент (2018/2019)
# 6)
SELECT
  player.id AS player_id,
  player.last_name,
  player.first_name,
  player_position.full_name,
  player.born_date
FROM player
  INNER JOIN (
               SELECT *
               FROM player
               ORDER BY player.last_name
               LIMIT 1, 30
             ) AS players USING (id)
  LEFT JOIN team_roster ON players.id = team_roster.player_id
  LEFT JOIN season ON team_roster.season_id = season.id
  LEFT JOIN player_position ON player.player_position_id = player_position.id
WHERE (season.start >= '2018-10-02' AND season.end <= '2019-05-01');

# /players/{player_id}
# Получить общую информацию об игроке

DROP FUNCTION IF EXISTS calcAge;

DELIMITER //
CREATE FUNCTION calcAge(bornDate DATE)
  RETURNS INT(11) DETERMINISTIC
  BEGIN
    RETURN (floor(datediff(curdate(), bornDate) / 365));
  END //
DELIMITER ;

# 7)
SELECT
  player.id                                       AS player_id,
  player.last_name,
  player.first_name,
  player.born_date,
  calcAge(born_date)                              AS age,
  GROUP_CONCAT(DISTINCT team.name SEPARATOR ', ') AS player_teams,
  city.name                                       AS born_city,
  player.photo,
  player_position.short_name
FROM player
  LEFT JOIN city ON player.birth_place_id = city.id
  LEFT JOIN team_roster ON player.id = team_roster.player_id
  LEFT JOIN team ON team_roster.team_id = team.id
  LEFT JOIN player_position ON player.player_position_id = player_position.id
WHERE player.id = 7;

# 8) Получить стаистику команд (количество побед, поражений, забитых и пропущенных голов и т.д.) , за которые выступал игрок по сезонам
SELECT
  player.id AS player_id,
  season.id AS season_id,
  season.start,
  season.end,
  team_name,
  game_count,
  goals_scored,
  goals_scored,
  difference,
  count_win,
  count_lose,
  count_overtime_win,
  count_overtime_lose,
  points
FROM player
  LEFT JOIN team_roster ON player.id = team_roster.player_id
  lEFT JOIN season ON team_roster.season_id = season.id
  LEFT JOIN (SELECT
               season_id,
               team_id,
               team_name,
               SUM(game_count)                       AS game_count,
               SUM(goals_scored)                     AS goals_scored,
               SUM(goals_missed)                     AS goals_missed,
               SUM(goals_scored) - SUM(goals_missed) AS difference,
               SUM(is_win)                           AS count_win,
               SUM(is_lose)                          AS count_lose,
               SUM(is_overtime_win)                  AS count_overtime_win,
               SUM(is_overtime_lose)                 AS count_overtime_lose,
               SUM(point)                            AS points
             FROM ((SELECT
                      game.season_id,
                      game.id                                                                                AS game_id,
                      COUNT(
                          game.id)                                                                           AS guest_game_count,
                      team.id                                                                                AS team_id,
                      team.name                                                                              AS team_name,
                      COUNT(
                          game.id)                                                                           AS game_count,
                      SUM(
                          game.home_team_goal_count)                                                         AS goals_scored,
                      SUM(
                          game.guest_team_goal_count)                                                        AS goals_missed,
                      SUM(game.guest_team_goal_count > game.home_team_goal_count AND game.time = '01:00:00') AS is_win,
                      SUM(game.guest_team_goal_count < game.home_team_goal_count AND game.time = '01:00:00') AS is_lose,
                      SUM(game.guest_team_goal_count > game.home_team_goal_count AND game.time >
                                                                                     '01:00:00')             AS is_overtime_win,
                      SUM(game.guest_team_goal_count < game.home_team_goal_count AND game.time >
                                                                                     '01:00:00')             AS is_overtime_lose,
                      SUM(getGamePoint(guest_team_goal_count, home_team_goal_count, time))                   AS point
                    FROM game
                      LEFT JOIN team ON game.guest_team_id = team.id
                      LEFT JOIN season ON game.season_id = season.id
                    WHERE game.end IS NOT NULL
                    GROUP BY season_id)
                   UNION ALL
                   (SELECT
                      game.season_id,
                      game.id                                                                                AS game_id,
                      COUNT(
                          game.id)                                                                           AS home_game_count,
                      team.id                                                                                AS team_id,
                      team.name                                                                              AS team_name,
                      COUNT(
                          game.id)                                                                           AS game_count,
                      SUM(
                          game.home_team_goal_count)                                                         AS goals_scored,
                      SUM(
                          game.guest_team_goal_count)                                                        AS goals_missed,
                      SUM(game.home_team_goal_count > game.guest_team_goal_count AND game.time = '01:00:00') AS is_win,
                      SUM(game.home_team_goal_count < game.guest_team_goal_count AND game.time = '01:00:00') AS is_lose,
                      SUM(game.home_team_goal_count > game.guest_team_goal_count AND game.time >
                                                                                     '01:00:00')             AS is_overtime_win,
                      SUM(game.home_team_goal_count < game.guest_team_goal_count AND game.time >
                                                                                     '01:00:00')             AS is_overtime_lose,
                      SUM(getGamePoint(home_team_goal_count, guest_team_goal_count, time))                   AS point
                    FROM game
                      LEFT JOIN team ON game.home_team_id = team.id
                      LEFT JOIN season ON game.season_id = season.id
                    WHERE game.end IS NOT NULL
                    GROUP BY season_id)) all_game
             GROUP BY season_id
             ORDER BY points DESC) AS stats ON stats.season_id = season.id
WHERE player.id = 1
GROUP BY season.id;

# Создать игрока
# 9)
INSERT player (first_name, last_name, born_date, birth_place_id, player_position_id, is_right_shoot) VALUE
  ('Александр', 'Овечкин', '1985-09-17', 1, 1, 1);

# Удалить игрока
# 10)
DELETE FROM player
WHERE id = 1;

# Обновита фото игрока
# 10)
UPDATE player
SET player.photo = '/img/some-photo.jpg';

# Поиск по фамилии или имени игрока
# 11)
SELECT
  player.id                                       AS player_id,
  player.last_name,
  player.first_name,
  player.born_date,
  calcAge(born_date)                              AS age,
  GROUP_CONCAT(DISTINCT team.name SEPARATOR ', ') AS player_teams,
  city.name                                       AS born_city,
  player.photo,
  player_position.short_name
FROM player
  LEFT JOIN city ON player.birth_place_id = city.id
  LEFT JOIN team_roster ON player.id = team_roster.player_id
  LEFT JOIN team ON team_roster.team_id = team.id
  LEFT JOIN player_position ON player.player_position_id = player_position.id
WHERE first_name LIKE '%Ник%' OR last_name LIKE '%Ник%'
GROUP BY player_id;

# Добавить игрока в команду
# 12)
INSERT INTO team_roster (season_id, team_id, player_id) VALUES
  (1, 2, 1);

# /calendar

DROP FUNCTION IF EXISTS getSeasionId;

DELIMITER //
CREATE FUNCTION getSeasionId(date DATE)
  RETURNS INT(11) DETERMINISTIC
  BEGIN
    RETURN (SELECT id
            FROM season
            WHERE season.start <= date AND season.end >= date);
  END //
DELIMITER ;

SELECT getSeasionId(CURDATE());

# 13) Получить игры за период [текущая дата - 2 недели; текущая дата + 2 недели] текущего сезона;
SELECT
  game.id AS game_id,
  home_team.id AS home_team_id,
  home_team.name AS home_team_name,
  guest_team_city.name,
  guest_team.id AS guest_team_id,
  guest_team.name AS guest_team_id,
  guest_team_city.name,
  game.start AS game_start
FROM game
  LEFT JOIN team AS home_team ON game.home_team_id = home_team.id
  LEFT JOIN team AS guest_team ON game.guest_team_id = guest_team.id
  LEFT JOIN team AS home_team_city ON game.home_team_id = home_team_city.id
  LEFT JOIN team AS guest_team_city ON game.guest_team_id = guest_team_city.id
WHERE  game.season_id = getSeasionId(CURDATE()) AND (game.start >= DATE_SUB(CURDATE(), INTERVAL 14 DAY) AND game.start <= DATE_ADD(CURDATE(), INTERVAL 14 DAY));

# 14) Получить игры за период [текущая дата - 2 недели; текущая дата + 2 недели] текущего сезона одной команды;
SELECT
  game.id AS game_id,
  home_team.id AS home_team_id,
  home_team.name AS home_team_name,
  guest_team_city.name,
  guest_team.id AS guest_team_id,
  guest_team.name AS guest_team_id,
  guest_team_city.name,
  game.start AS game_start
FROM game
  LEFT JOIN team AS home_team ON game.home_team_id = home_team.id
  LEFT JOIN team AS guest_team ON game.guest_team_id = guest_team.id
  LEFT JOIN team AS home_team_city ON game.home_team_id = home_team_city.id
  LEFT JOIN team AS guest_team_city ON game.guest_team_id = guest_team_city.id
WHERE (home_team.id = 1 OR guest_team.id = 1) AND game.season_id = getSeasionId(CURDATE())
      AND (game.start >= DATE_SUB(CURDATE(), INTERVAL 14 DAY) AND game.start <= DATE_ADD(CURDATE(), INTERVAL 14 DAY));

# 15) Добавить игру на сезон
INSERT INTO game (season_id, guest_team_id, home_team_id, time, guest_team_goal_count, home_team_goal_count, start, end) VALUES
  (1, 2, 1, null, 0, 0, '2018-10-09 19:00', null);

# 16) Изменить счет в игре
UPDATE game SET home_team_goal_count WHERE game.id = 1

# 17) /calendar/{game_id}/preview
# Получить результат последнего матча
SELECT
  game.id AS game_id,
  game.home_team_id,
  game.guest_team_id,
  game.home_team_goal_count,
  home_team.id AS home_team_id,
  home_team.name AS home_team_name,
  guest_team_city.name,
  guest_team.id AS guest_team_id,
  guest_team.name AS guest_team_id,
  guest_team_city.name,
  game.start AS game_start
FROM game
  LEFT JOIN team AS home_team ON game.home_team_id = home_team.id
  LEFT JOIN team AS guest_team ON game.guest_team_id = guest_team.id
  LEFT JOIN team AS home_team_city ON game.home_team_id = home_team_city.id
  LEFT JOIN team AS guest_team_city ON game.guest_team_id = guest_team_city.id;

# 18) /calendar/{game_id}/preview
# Получить результаты домашние игры команды А с командой Б
SELECT
  game.season_id,
  game.id                                                                                AS game_id,
  COUNT(
      game.id)                                                                           AS guest_game_count,
  team.id                                                                                AS team_id,
  team.name                                                                              AS team_name,
  COUNT(
      game.id)                                                                           AS game_count,
  SUM(
      game.home_team_goal_count)                                                         AS goals_scored,
  SUM(
      game.guest_team_goal_count)                                                        AS goals_missed,
  SUM(game.guest_team_goal_count > game.home_team_goal_count AND game.time = '01:00:00') AS is_win,
  SUM(game.guest_team_goal_count < game.home_team_goal_count AND game.time = '01:00:00') AS is_lose,
  SUM(game.guest_team_goal_count > game.home_team_goal_count AND game.time >
                                                                 '01:00:00')             AS is_overtime_win,
  SUM(game.guest_team_goal_count < game.home_team_goal_count AND game.time >
                                                                 '01:00:00')             AS is_overtime_lose,
  SUM(getGamePoint(guest_team_goal_count, home_team_goal_count, time))                   AS point
FROM game
  LEFT JOIN team ON game.home_team_id = team.id
  LEFT JOIN season ON game.season_id = season.id
WHERE game.end IS NOT NULL
GROUP BY game_id;
