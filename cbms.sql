-- MySQL dump 10.13  Distrib 5.6.21, for Win64 (x86_64)
--
-- Host: localhost    Database: cbms
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `tbl_admin`
--

DROP TABLE IF EXISTS `tbl_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_admin` (
  `username` varchar(10) NOT NULL DEFAULT '',
  `password` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_admin`
--

LOCK TABLES `tbl_admin` WRITE;
/*!40000 ALTER TABLE `tbl_admin` DISABLE KEYS */;
INSERT INTO `tbl_admin` VALUES ('admin','admin');
/*!40000 ALTER TABLE `tbl_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_bus`
--

DROP TABLE IF EXISTS `tbl_bus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_bus` (
  `route_no` varchar(10) NOT NULL,
  `bus_stop` varchar(100) NOT NULL,
  `bus_time` varchar(20) NOT NULL,
  `rate` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_bus`
--

LOCK TABLES `tbl_bus` WRITE;
/*!40000 ALTER TABLE `tbl_bus` DISABLE KEYS */;
INSERT INTO `tbl_bus` VALUES ('1','Kanjikuzhy','08:20 AM',3000),('1','Cherthala','08:00 AM',4000),('2','Kalarkode','08:45 AM',2500),('3','Ernakulam','06:30 AM',4000),('4','Kayamkulam','07:00 AM',5000),('1','Kuthiyathode','08:00 AM',2500);
/*!40000 ALTER TABLE `tbl_bus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_student`
--

DROP TABLE IF EXISTS `tbl_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_student` (
  `student_id` varchar(20) NOT NULL,
  `student_name` varchar(50) NOT NULL,
  `department` varchar(5) NOT NULL,
  `semester` int(11) NOT NULL,
  `route_no` varchar(10) DEFAULT NULL,
  `bus_stop` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_student`
--

LOCK TABLES `tbl_student` WRITE;
/*!40000 ALTER TABLE `tbl_student` DISABLE KEYS */;
INSERT INTO `tbl_student` VALUES ('10420001','Abhijith','CSE',100,'2','Kalarkode'),('10420002','Akhil Sabju','CSE',100,'1','Cherthala'),('10420005','Amit Nair','CSE',100,'2','Kalarkode'),('10420006','Anooj Krishnan G','CSE',100,'1','Cherthala'),('10420007','Arjun Sajeev','CSE',100,'2','Kalarkode'),('10420009','Binoy','CSE',100,'2','Kalarkode'),('10420012','Anoop Krishnan G','CSE',100,'4','Kayamkulam'),('10420025','Abdu','CSE',100,'4','Kayamkulam'),('10427777','Shabal','CSE',2,'1','Cherthala'),('11420001','Arjun','CSE',100,'2','Kalarkode');
/*!40000 ALTER TABLE `tbl_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_student_route`
--

DROP TABLE IF EXISTS `tbl_student_route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_student_route` (
  `student_id` varchar(20) NOT NULL,
  `route_no` varchar(10) NOT NULL,
  `bus_stop` varchar(100) NOT NULL,
  `bus_time` varchar(20) NOT NULL,
  `semester` int(11) NOT NULL,
  `reciept_no` int(11) NOT NULL AUTO_INCREMENT,
  `fees_paid` int(11) NOT NULL,
  `dues` int(11) NOT NULL,
  `balance` int(11) NOT NULL,
  PRIMARY KEY (`reciept_no`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `tbl_student_route_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `tbl_student` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_student_route`
--

LOCK TABLES `tbl_student_route` WRITE;
/*!40000 ALTER TABLE `tbl_student_route` DISABLE KEYS */;
INSERT INTO `tbl_student_route` VALUES ('10420009','2','Kalarkode','08:45 AM',1,47,2500,0,0),('10420002','1','Cherthala','08:00 AM',3,48,4000,0,0),('10420002','1','Cherthala','08:00 AM',4,49,3500,500,0),('10420001','2','Kalarkode','08:45 AM',4,50,2500,0,0),('10427777','1','Cherthala','08:00 AM',1,51,500,0,0),('10427777','1','Cherthala','08:00 AM',2,52,4500,0,500);
/*!40000 ALTER TABLE `tbl_student_route` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-23 22:17:47
