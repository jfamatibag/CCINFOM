CREATE DATABASE  IF NOT EXISTS `dbprojectapp` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `dbprojectapp`;
-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: dbprojectapp
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `branch` (
  `branch_code` varchar(5) NOT NULL,
  `branch_name` varchar(50) NOT NULL,
  `street_address` varchar(100) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`branch_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES 
('0000','BD - Cebu', '3rd Level, Ayala Center Cebu, Cebu Business Park', 'Cebu'),
('1111','BD - Glorietta','Space 3-056, 3/F, New Glorietta, Ayala Center','Makati'),
('2222','BD - One Ayala','Unit D-402, Level 4, One Ayala Mall, EDSA cor. Ayala Ave.','Makati'),
('3333','BD - Trinoma','#1081, Level 1, Trinoma, EDSA cor. North Ave.', 'Quezon'),
('4444','BD - Festival Supermall', 'Unit 4050, West Wing, 3/F, Festival Mall, Filinvest City, Alabang', 'Muntinlupa'),
('5555','BD - Greenhills', 'Unit L435A-B, Level 4, Greenhills Mall', 'San Juan'),
('6666','BD - Dumaguete','3rd Floor, Robinsons Dumaguete, Dumaguete Business Park, South Road, Calindagan 6200', 'Dumaguete'),
('7777','BD - Antipolo','Upper Ground 230E & 230F, Sumulong cor. Circumferential Rd., Brgy. Dela Paz', 'Antipolo'),
('8888', 'BD - Shangri-La', 'Lower Ground Level, Techno Hub, Shangri-La Plaza, EDSA cor. Shaw Blvd.' , 'Mandaluyong'),
('9999', 'BD - Baguio', 'Unit 312, 3/F, SM City Baguio, Luneta Hill, Upper Session Rd.', 'Baguio');
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `customer` (
  `customer_number` int(11) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `registration_date` datetime NOT NULL,
  `membership_points` int(11) DEFAULT '0',
  PRIMARY KEY (`customer_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `game` (
  `game_code` varchar(5) NOT NULL,
  `branch_code` varchar(5) NOT NULL,
  `game_name` varchar(100) NOT NULL,
  `genre` varchar(50) DEFAULT NULL,
  `quantity_in_stock` int(11) DEFAULT '0',
  `discontinued` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`game_code`,`branch_code`),
  KEY `FK_game_branch_idx` (`branch_code`),
  CONSTRAINT `FK_game_branch` FOREIGN KEY (`branch_code`) REFERENCES `branch` (`branch_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sales` (
  `sale_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_number` int(11) NOT NULL,
  `game_code` varchar(5) NOT NULL,
  `branch_code` varchar(5) NOT NULL,
  `purchase_date` datetime NOT NULL,
  `amount_paid` double NOT NULL,
  `genre` varchar(50) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`sale_id`),
  KEY `FK_sales_customer_idx` (`customer_number`),
  KEY `FK_sales_branch_idx` (`branch_code`),
  KEY `FK_sales_game_idx` (`game_code`,`branch_code`),
  CONSTRAINT `FK_sales_customer` FOREIGN KEY (`customer_number`) REFERENCES `customer` (`customer_number`),
  CONSTRAINT `FK_sales_branch` FOREIGN KEY (`branch_code`) REFERENCES `branch` (`branch_code`),
  CONSTRAINT `FK_sales_game` FOREIGN KEY (`game_code`, `branch_code`) REFERENCES `game` (`game_code`, `branch_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
