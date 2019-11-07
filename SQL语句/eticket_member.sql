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

 Date: 07/11/2019 23:16:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for eticket_member
-- ----------------------------
DROP TABLE IF EXISTS `eticket_member`;
CREATE TABLE `eticket_member`  (
  `sharding_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `blacklist_flag` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `credit_point` int(11) NOT NULL,
  `membership_level` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `telephone_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nickname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `wechat_openid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`sharding_id`, `user_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
