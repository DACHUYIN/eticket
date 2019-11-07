/*
 Navicat Premium Data Transfer

 Source Server         : AWS-MYSQL-1
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : 13.229.123.46:3306
 Source Schema         : eticket

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 07/11/2019 23:16:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for eticket_foodticket
-- ----------------------------
DROP TABLE IF EXISTS `eticket_foodticket`;
CREATE TABLE `eticket_foodticket`  (
  `sharding_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `telephone_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ticket_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` decimal(19, 2) NOT NULL,
  `service_charge` decimal(19, 2) NOT NULL,
  `total_price` decimal(19, 2) NOT NULL,
  `upload_flag` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `img_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `qr_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `term_validity` int(11) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`sharding_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
