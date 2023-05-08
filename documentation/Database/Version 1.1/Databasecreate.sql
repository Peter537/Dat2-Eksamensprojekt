CREATE DATABASE  IF NOT EXISTS `fogcarport` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `fogcarport`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 64.226.103.200    Database: fogcarport
-- ------------------------------------------------------
-- Server version	8.0.32-0ubuntu0.20.04.2

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
-- Table structure for table `carport_order`
--

DROP TABLE IF EXISTS `carport_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carport_order` (
                                 `carport_order_id` int NOT NULL AUTO_INCREMENT,
                                 `address` varchar(90) DEFAULT NULL,
                                 `fk_employee_email` varchar(45) DEFAULT NULL,
                                 `fk_customer_email` varchar(45) DEFAULT NULL,
                                 `fk_roof_id` int DEFAULT NULL,
                                 `width` float DEFAULT NULL,
                                 `length` float DEFAULT NULL,
                                 `min_height` float DEFAULT NULL,
                                 `toolroom_width` float DEFAULT NULL,
                                 `toolroom_length` float DEFAULT NULL,
                                 `remarks` varchar(90) DEFAULT NULL,
                                 PRIMARY KEY (`carport_order_id`),
                                 UNIQUE KEY `carport_order_id_UNIQUE` (`carport_order_id`),
                                 KEY `fk_carport_order_employee1_idx` (`fk_employee_email`),
                                 KEY `fk_carport_order_customer1_idx` (`fk_customer_email`),
                                 KEY `fk_carport_order_roof1_idx` (`fk_roof_id`),
                                 CONSTRAINT `fk_carport_order_customer1` FOREIGN KEY (`fk_customer_email`) REFERENCES `customer` (`email`),
                                 CONSTRAINT `fk_carport_order_employee1` FOREIGN KEY (`fk_employee_email`) REFERENCES `employee` (`email`),
                                 CONSTRAINT `fk_carport_order_roof1` FOREIGN KEY (`fk_roof_id`) REFERENCES `roof` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
                            `email` varchar(45) NOT NULL,
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(45) NOT NULL,
                            `password` varchar(45) NOT NULL,
                            `phonenumber` varchar(45) DEFAULT NULL,
                            `address_1` varchar(90) DEFAULT NULL,
                            `zipcode_1` int DEFAULT NULL,
                            `address_2` varchar(90) DEFAULT NULL,
                            `zipcode_2` int DEFAULT NULL,
                            `address_3` varchar(90) DEFAULT NULL,
                            `zipcode_3` int DEFAULT NULL,
                            PRIMARY KEY (`email`),
                            UNIQUE KEY `email_UNIQUE` (`email`),
                            UNIQUE KEY `id_UNIQUE` (`id`),
                            KEY `fk_customer_zip1_idx` (`zipcode_1`),
                            KEY `fk_customer_zip2_idx` (`zipcode_2`),
                            KEY `fk_customer_zip3_idx` (`zipcode_3`),
                            CONSTRAINT `fk_customer_zip1` FOREIGN KEY (`zipcode_1`) REFERENCES `zip` (`zipcode`),
                            CONSTRAINT `fk_customer_zip2` FOREIGN KEY (`zipcode_2`) REFERENCES `zip` (`zipcode`),
                            CONSTRAINT `fk_customer_zip3` FOREIGN KEY (`zipcode_3`) REFERENCES `zip` (`zipcode`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `address` varchar(90) DEFAULT NULL,
                              `zipcode` int DEFAULT NULL,
                              `name` varchar(45) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `id_UNIQUE` (`id`),
                              KEY `fk_department_zip1_idx` (`zipcode`),
                              CONSTRAINT `fk_department_zip1` FOREIGN KEY (`zipcode`) REFERENCES `zip` (`zipcode`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
                            `email` varchar(45) NOT NULL,
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(45) NOT NULL,
                            `password` varchar(45) NOT NULL,
                            `work_phonenumber` varchar(45) DEFAULT NULL,
                            `private_phonenumber` varchar(45) DEFAULT NULL,
                            `fk_position` varchar(45) DEFAULT NULL,
                            `fk_department_id` int DEFAULT NULL,
                            PRIMARY KEY (`email`),
                            UNIQUE KEY `email_UNIQUE` (`email`),
                            UNIQUE KEY `id_UNIQUE` (`id`) /*!80000 INVISIBLE */,
                            KEY `fk_employee_department1_idx` (`fk_department_id`),
                            KEY `fk_employee_position1_idx` (`fk_position`),
                            CONSTRAINT `fk_employee_department` FOREIGN KEY (`fk_department_id`) REFERENCES `department` (`id`),
                            CONSTRAINT `fk_employee_position1` FOREIGN KEY (`fk_position`) REFERENCES `position` (`position`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lumber`
--

DROP TABLE IF EXISTS `lumber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lumber` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `length` float NOT NULL,
                          `type` int NOT NULL,
                          `amount` int DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `id_UNIQUE` (`id`),
                          KEY `fk_lumber_lumbertype1_idx` (`type`),
                          CONSTRAINT `fk_lumber_lumbertype1` FOREIGN KEY (`type`) REFERENCES `lumbertype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lumbertype`
--

DROP TABLE IF EXISTS `lumbertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lumbertype` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `thickness` float DEFAULT NULL,
                              `width` float DEFAULT NULL,
                              `name` varchar(45) DEFAULT NULL,
                              `meter_price` float DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position` (
                            `position` varchar(45) NOT NULL,
                            PRIMARY KEY (`position`),
                            UNIQUE KEY `position_UNIQUE` (`position`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roof`
--

DROP TABLE IF EXISTS `roof`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roof` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `squaremeter_price` float NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zip`
--

DROP TABLE IF EXISTS `zip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zip` (
                       `zipcode` int NOT NULL,
                       `city_name` varchar(45) NOT NULL,
                       PRIMARY KEY (`zipcode`),
                       UNIQUE KEY `zipcode_UNIQUE` (`zipcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'fogcarport'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-08 13:11:50
