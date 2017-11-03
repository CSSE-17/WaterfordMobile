-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: waterford_mobile
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supplier` (
  `Supplier_ID` varchar(20) NOT NULL,
  `Supplier_name` varchar(45) DEFAULT NULL,
  `Description` varchar(60) DEFAULT NULL,
  `Address` varchar(45) DEFAULT NULL,
  `Tpnumber` varchar(12) DEFAULT NULL,
  `Email` varchar(40) DEFAULT NULL,
  `Date_joined` varchar(15) DEFAULT NULL,
  `Bank` varchar(40) DEFAULT NULL,
  `Branch` varchar(45) DEFAULT NULL,
  `Accnt_no` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Supplier_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES ('SUP0001','CNC Company','	','Makola South, Makola','0112387211','lamb@gmail.com','2016-09-12','Sampath Bank','Kiribathgoda','0120320122552'),('SUP0002','Dinux pvt ltd','','Rambukkana road, Kegalle','0112456232','dinux@gmail.com','2016-09-07','Peoples Bank','Kadawatha','2156564855427'),('SUP0003','Kalpan Company','','Kadawatha road, Kiribathgoda','0775484678','nir@gmail.com','2016-09-07','Bank of Ceylon','Kiribathgoda','654896496942684'),('SUP0004','Kumara & Sons','','Flower road, Ja-ela','0112045784','kumar@gmail.com','2016-09-19','Sampath Bank','Ja-ela','32894123214562'),('SUP0005','Ishara','','Galagedara road, nawala','0112458775','isha@gmail.com','2016-09-20','Peoples Bank','Nawala','256485496241586'),('SUP0006','Mahen Company','','Degaawatha road, ranala','0124584125','saha@gmail.com','2016-09-14','Commercial Bank','ranala','2458659462412458'),('SUP0007','99group pvt Ltd','','Kins road, Colombo 3','0325685472','grp99@gmail.com','2016-09-29','HSBC Bank','Kirillawala','215486859452'),('SUP0008','Devinda ','','Nawam mawatha, Galle','0112458455','dev22@gmail.com','2016-09-26','Sanasa Development','Hikkaduwa','10215478120161'),('SUP0009','Link toys','','Mabola, Waththala','0114587544','links@gmail.com','2016-09-05','Peoples Bank','Waththala','01245846854621'),('SUP0010','P&M Groups','','Raja mawatha, Kelaniya','0124845741','pandm@yahoo.com','2016-09-20','DFCC Bank','Kelaniya','0124845765214'),('SUP0011','MAs groups pvt Ltd','','Malinda, Kapugoda','0114579845','masgroup@yahoo.com','2016-10-06','Bank Of Ceylon','Malinda','012458457652'),('SUP0012','Ariyarathna Sons','','Rawanagala,Kandana','0245845732','ariya@gmail.com','2016-09-21','Nations Trust Bank','Kandana','24587542195'),('SUP0013','123Toys pvt. Ltd ','','Rabukkana road, Kegalle','0112458472','123tys@gmail.com','2016-09-12','Bank Of Ceylon','Kegalle Town','02154784512'),('SUP0014','PowerToy','','Boyer road, Biyagama','0114578542','power@gmail.com','2016-09-19','HSBC Bank','Biyagama','0124578451269'),('SUP0015','Unilievers pvt ltd','','Colombo road, Meepe','0124857452','uniliev@gmail.com','2016-09-20','Commercial Bank','Meepe','01245875956'),('SUP0016','Grand Toys','dsdsd','Malatha Road, Kandana','0112485724','grnd@gmail.com','2017-11-09','Nations Trust Bank','Kandana','021457845124');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `user_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `acc_type` varchar(45) DEFAULT NULL,
  `access_privileges` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES ('admin','1c63129ae9db9c60c3e8aa94d3e00495','mahendrathennakoon@gmail.com','Mahendra','Tennakoon','admin','101010');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-03 15:38:19
