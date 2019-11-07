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

 Date: 07/11/2019 23:16:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for eticket_payment
-- ----------------------------
DROP TABLE IF EXISTS `eticket_payment`;
CREATE TABLE `eticket_payment`  (
  `sharding_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `payment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `telephone_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `wechat_openid_buyer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `wechat_openid_seller` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` decimal(19, 2) NOT NULL,
  `service_charge` decimal(18, 2) NOT NULL,
  `total_price` decimal(18, 2) NOT NULL,
  `payment_status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`sharding_id`, `payment_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
