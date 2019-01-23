-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hockey_protocol
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_city_id` (`id`),
  KEY `IN_city_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,''),(22,''),(16,'asdf'),(17,'asdf'),(18,'asdf'),(19,'asdf'),(20,'asdf'),(21,'asdf'),(13,'sdfasf'),(15,'sdfasf'),(7,'sdsdafs'),(14,'socialOauth'),(12,'Yoshkaddds'),(8,'yoshkar-ola'),(9,'yoshkar-ola'),(10,'yoshkar-olsds'),(4,'Вашингтон'),(3,'Нижний Тагил'),(5,'Питсбург'),(6,'Тампа'),(2,'Челябинск');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `season_id` int(11) NOT NULL,
  `guest_team_id` int(11) NOT NULL,
  `home_team_id` int(11) NOT NULL,
  `time` time DEFAULT NULL,
  `guest_team_goal_count` int(11) DEFAULT '0',
  `home_team_goal_count` int(11) DEFAULT '0',
  `start` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_game_id` (`id`),
  UNIQUE KEY `UN_game_home_team_start` (`home_team_id`,`start`),
  UNIQUE KEY `UN_game_guest_team_start` (`guest_team_id`,`start`),
  KEY `IN_game_season_id` (`season_id`),
  KEY `IN_game_guest_team_id` (`guest_team_id`),
  KEY `IN_game_guest_home_team_id` (`home_team_id`),
  KEY `IN_game_time` (`time`),
  KEY `IN_game_guest_team_goal_count` (`guest_team_goal_count`),
  KEY `IN_game_home_team_goal_count` (`home_team_goal_count`),
  KEY `IN_game_start` (`start`),
  KEY `IN_game_end` (`end`),
  CONSTRAINT `game_ibfk_1` FOREIGN KEY (`season_id`) REFERENCES `season` (`id`),
  CONSTRAINT `game_ibfk_2` FOREIGN KEY (`guest_team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `game_team_id_fk` FOREIGN KEY (`home_team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (1,1,2,1,NULL,0,0,'2018-10-09 19:00:00',NULL),(2,1,1,2,'01:00:00',0,0,'2018-10-04 19:00:00','2018-10-04 21:00:02'),(3,2,1,2,'01:00:00',5,4,'2017-10-04 19:00:00','2017-10-04 21:05:00'),(4,2,1,2,'01:00:00',7,1,'2017-10-15 19:00:00','2017-10-15 21:05:05'),(5,2,2,1,'01:00:00',0,5,'2017-10-16 19:00:00','2017-10-16 21:05:00'),(6,2,1,3,'01:00:07',0,3,'2017-10-20 19:00:00','2017-10-20 21:05:00'),(7,2,1,2,'01:00:00',2,3,'2017-10-25 19:00:00','2017-10-25 21:05:00'),(8,1,3,1,NULL,0,0,'2019-01-24 19:00:00',NULL);
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(254) DEFAULT NULL,
  `born_date` date DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `birth_place_id` int(11) DEFAULT NULL,
  `player_position_id` int(11) DEFAULT NULL,
  `is_right_shoot` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_player_id` (`id`),
  KEY `FN_player_born_date` (`born_date`),
  KEY `IN_player_photo` (`photo`),
  KEY `UN_player_birth_place_id` (`birth_place_id`),
  KEY `UN_player_player_position_id` (`player_position_id`),
  KEY `IN_player_player_is_right_shoot` (`is_right_shoot`),
  FULLTEXT KEY `FN_player_first_name` (`first_name`),
  FULLTEXT KEY `FN_player_last_name` (`last_name`),
  CONSTRAINT `player_ibfk_1` FOREIGN KEY (`player_position_id`) REFERENCES `player_position` (`id`) ON DELETE CASCADE,
  CONSTRAINT `player_ibfk_2` FOREIGN KEY (`birth_place_id`) REFERENCES `city` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (1,'Александр','Овечкин','1985-09-17',NULL,1,1,1),(2,'Илья','Ковальчук','1983-04-15',NULL,1,1,1),(3,'Никита','Кучеров','1993-06-17',NULL,1,2,0),(4,'Евгений','Кузнецов','1992-05-19',NULL,1,2,0),(5,'Евгений','Малкин','1992-05-19',NULL,1,3,0),(6,'Сидни','Кросби','1987-08-07',NULL,1,3,1),(7,'Коннор','Макдэвид','1997-01-17',NULL,1,2,0),(8,'Николас','Бэкстрем','1986-11-23',NULL,1,3,0),(9,'Ти Джей','Оши','1986-12-23',NULL,1,2,1),(10,'Джон','Карслон','1990-01-10',NULL,1,5,1),(11,'Мэтт','Нисканен','1986-12-06',NULL,1,5,1),(12,'Дмитрий','Орлов','1991-07-21',NULL,1,4,0),(13,'Брейден','Холтби','1989-09-12',NULL,1,1,0),(14,'Фил','Кессел','1987-10-02',NULL,5,2,0),(15,'Крис','Летанг','1987-04-24',NULL,5,5,1),(16,'Оли','Мяяата','1994-09-22',NULL,5,4,0),(17,'Сергей','Бобровский','1994-05-25',NULL,5,1,0),(18,'  dto.getFirstName() ',' dto.getLastName()','1993-06-17',NULL,1,1,0);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_position`
--

DROP TABLE IF EXISTS `player_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) DEFAULT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_player_position_id` (`id`),
  KEY `IN_player_position_fullname` (`full_name`),
  KEY `player_position_short_name` (`short_name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_position`
--

LOCK TABLES `player_position` WRITE;
/*!40000 ALTER TABLE `player_position` DISABLE KEYS */;
INSERT INTO `player_position` VALUES (1,'левый нападающий','ЛН'),(2,'правый нападающий','ПрН'),(3,'центральный нападающий','ЦН'),(4,'левый защитник','ЛЗ'),(5,'правый защитник','ПЗ'),(11,'вратарь','В');
/*!40000 ALTER TABLE `player_position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_role_id` (`id`),
  KEY `IN_role_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'админ'),(2,'пользователь');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `season`
--

DROP TABLE IF EXISTS `season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `season` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` date DEFAULT NULL,
  `end` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_season_id` (`id`),
  KEY `IN_season_start` (`start`),
  KEY `IN_season_end` (`end`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `season`
--

LOCK TABLES `season` WRITE;
/*!40000 ALTER TABLE `season` DISABLE KEYS */;
INSERT INTO `season` VALUES (1,'2018-10-03','2019-04-20'),(2,'2017-10-05','2018-04-24');
/*!40000 ALTER TABLE `season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `foundation_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_team_id` (`id`),
  KEY `IN_team_avatar` (`avatar`),
  KEY `IN_team_foundation_date` (`foundation_date`),
  FULLTEXT KEY `FN_team_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (1,'Вашингтон Кэпиталз',NULL,'1974-01-01'),(2,'Питтсбург Пингвинз',NULL,'1967-01-01'),(3,'Тампа-Бэй Лайтнинг',NULL,'1992-01-01');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_in_city`
--

DROP TABLE IF EXISTS `team_in_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_in_city` (
  `team_id` int(11) NOT NULL,
  `city_id` int(11) NOT NULL,
  KEY `UN_team_in_city_team_id` (`team_id`),
  KEY `IN_team_in_city_city_id` (`city_id`),
  CONSTRAINT `team_in_city_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `team_in_city_ibfk_2` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_in_city`
--

LOCK TABLES `team_in_city` WRITE;
/*!40000 ALTER TABLE `team_in_city` DISABLE KEYS */;
INSERT INTO `team_in_city` VALUES (1,4),(2,5),(3,6);
/*!40000 ALTER TABLE `team_in_city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_roster`
--

DROP TABLE IF EXISTS `team_roster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_roster` (
  `season_id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  KEY `UN_team_roster_season_id` (`season_id`),
  KEY `IN_team_roster_team_id` (`team_id`),
  KEY `IN_team_roster_player_id` (`player_id`),
  KEY `UN_team_roster_season__id_team_id__player_id` (`season_id`,`team_id`,`player_id`),
  KEY `UN_team_roster_season__id__player_id` (`season_id`,`player_id`),
  CONSTRAINT `team_roster_ibfk_1` FOREIGN KEY (`season_id`) REFERENCES `season` (`id`),
  CONSTRAINT `team_roster_ibfk_2` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `team_roster_ibfk_3` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_roster`
--

LOCK TABLES `team_roster` WRITE;
/*!40000 ALTER TABLE `team_roster` DISABLE KEYS */;
INSERT INTO `team_roster` VALUES (1,1,4),(1,1,8),(1,1,9),(1,1,10),(1,1,11),(1,1,12),(1,1,13),(1,2,5),(1,2,6),(1,2,7),(1,2,14),(1,2,15),(1,2,16),(1,2,17),(2,1,1),(2,1,4),(2,1,7),(2,1,8),(2,1,9),(2,1,10),(2,1,11),(2,1,12),(2,1,13),(2,2,5),(2,2,6),(2,2,14),(2,2,15),(2,2,16),(2,2,17);
/*!40000 ALTER TABLE `team_roster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(254) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_user_id` (`id`),
  KEY `UN_user_password` (`password`),
  KEY `UN_user_avatar` (`avatar`),
  FULLTEXT KEY `IN_user_username` (`username`),
  FULLTEXT KEY `IN_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin@gmail.com','admin',''),(2,'user','user@gmail.com','user','');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `UN_user_role_user_id` (`user_id`),
  KEY `IN_user_role_role_id` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-23 18:11:36
