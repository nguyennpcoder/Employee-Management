-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: finalexam4
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(72) NOT NULL,
  `role` varchar(8) NOT NULL,
  `username` varchar(50) NOT NULL,
  `department_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gex1lmaqpg0ir5g1f5eftyaa1` (`username`),
  KEY `FK2g0xie5ofcl4oy9s3qtlwxqwc` (`department_id`),
  CONSTRAINT `FK2g0xie5ofcl4oy9s3qtlwxqwc` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'2023-09-09 13:47:40.192000','Nguyen','Nguyen','$2a$10$v8FHPWDwqPbVCoDMGY/7/e1Qtwklog8OS0XOrCP5AMgTtMwr5Yfrm','ADMIN','nguyenit',NULL),(3,'2023-09-09 14:10:42.315000','it','nguyen','$2a$10$llDXVOFbr.iB5XUF0Gysb.ybWKNUr/ZnnXwgtzXL/YBvnO0vF7b1C','ADMIN','nguyendev',NULL),(5,'2023-09-10 14:59:56.987000','a','nguyen van a','$2a$10$nSTIc64yPY1TbAnx.Bo1a.JE/tajsE18Oh.U19XL7VmSm.CcvT6zy','ADMIN','vana',NULL),(6,'2023-09-13 10:02:17.785000','b','nguyen van b','$2a$10$bXteS9HhUxCGtzOKR7ccF.TvYAwzPDXFtDoHi11zafySywar8TKV6','MANAGER','vanb',NULL),(7,'2023-09-13 10:03:29.567000','d','nguyen van d','$2a$10$bnNybyCB5wQqkqQpH.fZceHu.EnQuovU3Wvgt1hsTKBs7jEmWNYnS','EMPLOYEE','vand',NULL),(8,'2023-09-13 10:07:42.869000','c','nguyen van c','$2a$10$bmDfcNpnH7FukC1Fgxa8P.PrI9HzS.Lp5T26dGzH8Fm/q/cockwfa','MANAGER','vanc ',NULL),(9,'2023-09-16 15:26:22.313000','nguyen','nguyen phuco nguyen','$2a$10$4MvdV/PX85Und4r6owFk4.HHFGxmYh5ggfC3sKyFxhTVnXcHX.uPm','ADMIN','nguyenjava',NULL),(10,'2023-09-20 10:29:10.821000','nguyen','nguyen phuoc','$2a$10$DZNlK819DSjQg6DumnAhU.rsBVZMcePPpgccQK1Ggv5gIwthw26aK','ADMIN','ngynit',2),(12,'2023-09-21 09:54:50.258908','y','nguyen bui nhut','$2a$10$091czSt8KVx2odn1fEPtQOqQiP.knTxD3OlIRptEqETcmn7nqwanu','EMPLOYEE','ybui',NULL),(13,'2023-09-22 13:46:47.829736','huy','tran a','$2a$10$E219fhWEsexlC0y5fVLWlOegNDdV6JJUsSQV8BATZhBhqDnn/8Md2','EMPLOYEE','ahuy',2),(14,'2023-10-07 13:10:24.623544','a huy ','Tran  ','$2a$10$a4U.8qdZOiNohfO66Q8rXuE5wQJrRrcEg6qkkiEEOGvYpHpNoIB72','ADMIN','ahuy247',NULL),(18,'2023-11-27 14:59:15.998600','khoa','huynh minh','$2a$10$78RzZ5fdJSHVBEK2bwDCpuVU/j79t4YT6KMqpvC01jCegb.HI2x3G','EMPLOYEE','khoa',NULL),(19,'2023-11-27 15:48:38.673044','ngyn','nguyen phuoc','$2a$10$SOiGw6umRE1RkEmF/PgLIeplWswAlU9/i25Cpx5D6vvNETQKQgSu.','ADMIN',' ngyn',4);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `total_members` int NOT NULL,
  `type` varchar(15) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1t68827l97cwyxo9r1u6t4p7d` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (2,'2023-09-09 14:13:26.571000','Quan Ly',1,'DEVELOPER','2023-09-10 14:59:38.190000'),(4,'2023-11-27 15:43:52.607430','66DongThap',5,'DEVELOPER','2023-11-27 15:44:14.683857');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-02 14:50:31
