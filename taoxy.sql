/*
 Navicat Premium Data Transfer

 Source Server         : www.taoxy.online
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : www.taoxy.online:1993
 Source Schema         : taoxy

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 15/03/2019 16:26:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sense_agro_admin
-- ----------------------------
DROP TABLE IF EXISTS `sense_agro_admin`;
CREATE TABLE `sense_agro_admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `member_password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `register_time` int(11) NOT NULL DEFAULT 0,
  `update_time` int(11) NOT NULL DEFAULT 0,
  `is_banned` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sense_agro_member
-- ----------------------------
DROP TABLE IF EXISTS `sense_agro_member`;
CREATE TABLE `sense_agro_member`  (
  `member_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NOT NULL DEFAULT '',
  `member_password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `member_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `member_modify_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `member_sex` int(11) NOT NULL DEFAULT 0,
  `member_nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `member_country` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `member_province` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `member_city` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `member_area` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `member_mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `member_openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `member_unionid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `register_time` int(11) NOT NULL DEFAULT 0,
  `update_time` int(11) NOT NULL DEFAULT 0,
  `is_banned` int(11) NOT NULL DEFAULT 0,
  `register_from` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`member_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2780 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sense_agro_specialist
-- ----------------------------
DROP TABLE IF EXISTS `sense_agro_specialist`;
CREATE TABLE `sense_agro_specialist`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '专家id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '专家名字',
  `uname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '专家名字',
  `passwd` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '专家密码',
  `avater` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '头像',
  `mark` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '专家标示',
  `visit` int(11) NOT NULL DEFAULT 0 COMMENT '接诊次数',
  `work_age` int(4) NOT NULL DEFAULT 0 COMMENT '行业经验',
  `area` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '熟悉地区',
  `origin_price` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '原始价格',
  `adjust_price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '调整后的价格',
  `description` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '专家的描述',
  `add_time` int(11) NOT NULL DEFAULT 0 COMMENT '添加时间',
  `crop_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '擅长作物',
  `cover_image` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '专家封面图',
  `skill` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '擅长技能、领域',
  `time_show` int(11) NOT NULL DEFAULT 0 COMMENT '显示时间',
  `is_online` tinyint(1) NOT NULL COMMENT '专家是否在线默认0离线1忙碌中9在线',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_name`(`uname`) USING BTREE COMMENT '手机号唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sense_agro_user
-- ----------------------------
DROP TABLE IF EXISTS `sense_agro_user`;
CREATE TABLE `sense_agro_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `specialist_id` int(11) NOT NULL DEFAULT 0 COMMENT '可以查看多端登录',
  `add_time` int(11) NOT NULL DEFAULT 0 COMMENT '添加时间',
  `valid_time` int(11) NOT NULL DEFAULT 0 COMMENT '有效时间',
  `sessionId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户token 可以根据次数反应登录次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 525 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
