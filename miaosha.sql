/*
 Navicat Premium Data Transfer

 Source Server         : tos_mysql
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : 101.33.228.113:3306
 Source Schema         : miaosha

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 13/04/2022 19:51:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for deposit_record
-- ----------------------------
DROP TABLE IF EXISTS `deposit_record`;
CREATE TABLE `deposit_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '存款记录id',
  `serial_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流水号',
  `activity_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动id',
  `goods_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品id',
  `member_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `deposit` decimal(15, 0) NOT NULL COMMENT '存入金额',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态(1成功,0失败)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_deleted` tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段，0表示没被删除，1表示已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `users_id`(`member_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存款记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stock_log
-- ----------------------------
DROP TABLE IF EXISTS `stock_log`;
CREATE TABLE `stock_log`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '库存流水主键',
  `goods_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品id',
  `activity_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动id',
  `amount` int(11) NOT NULL COMMENT '购买数量',
  `status` tinyint(4) NOT NULL COMMENT '库存流水状态',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_deleted` tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段，0表示没被删除，1表示已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tos_account
-- ----------------------------
DROP TABLE IF EXISTS `tos_account`;
CREATE TABLE `tos_account`  (
  `balance` decimal(10, 0) NULL DEFAULT NULL COMMENT '银行余额',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tos_activity
-- ----------------------------
DROP TABLE IF EXISTS `tos_activity`;
CREATE TABLE `tos_activity`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `goods_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '活动名称',
  `cover_image` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动封面图片地址',
  `describe` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动描述',
  `stock` int(11) NOT NULL COMMENT '活动产品数量',
  `rest_stock` int(11) NOT NULL COMMENT '活动产品数量剩余',
  `start_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '活动开始时间',
  `end_time` datetime(0) NOT NULL COMMENT '活动结束时间',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态(0关闭，1开启)',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志(0未删除，1已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `product_id`(`goods_id`) USING BTREE COMMENT '活动id'
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '活动记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tos_activity_rule
-- ----------------------------
DROP TABLE IF EXISTS `tos_activity_rule`;
CREATE TABLE `tos_activity_rule`  (
  `id` int(11) NOT NULL,
  `activity_id` int(11) NULL DEFAULT NULL COMMENT '活动id',
  `rule_id` int(11) NULL DEFAULT NULL COMMENT '规则id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tos_goods
-- ----------------------------
DROP TABLE IF EXISTS `tos_goods`;
CREATE TABLE `tos_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '产品id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品名称',
  `term` int(11) NOT NULL COMMENT '期限',
  `annual_profit` decimal(15, 2) NOT NULL COMMENT '年利率',
  `initial_deposit` decimal(15, 2) NOT NULL COMMENT '起存金额',
  `incremental_deposit` decimal(15, 2) NOT NULL COMMENT '递增金额',
  `value_date` datetime(0) NOT NULL COMMENT '起息日',
  `person_limit` decimal(15, 2) NOT NULL COMMENT '单人限额',
  `day_limit` decimal(15, 2) NOT NULL COMMENT '单日限额',
  `settlement_way` int(11) NOT NULL COMMENT '结息方式',
  `due_date` datetime(0) NOT NULL COMMENT '到期日',
  `risk_level` int(11) NOT NULL COMMENT '风险等级',
  `stock` int(11) NOT NULL COMMENT '库存',
  `status` tinyint(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态(0 下架，1 上架)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_deleted` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段，0表示没被删除，1表示已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tos_member
-- ----------------------------
DROP TABLE IF EXISTS `tos_member`;
CREATE TABLE `tos_member`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'http://localhost/image/b66ebfd7-2fe7-46c6-ba18-9233a3204bf1.png' COMMENT '头像',
  `id_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `sex` tinyint(4) NULL DEFAULT 1 COMMENT '性别',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '随机盐',
  `balance` decimal(15, 0) NULL DEFAULT NULL COMMENT '余额',
  `work_condition` tinyint(4) NULL DEFAULT 1 COMMENT '工作状态(0 失业，1 有工作)',
  `dishonest` tinyint(4) NULL DEFAULT 0 COMMENT '是否失信(0 否，1 是)',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_deleted` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除字段，0表示没被删除，1表示已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_card`(`id_card`) USING BTREE COMMENT '唯一身份证',
  UNIQUE INDEX `phone`(`phone`) USING BTREE COMMENT '唯一电话'
) ENGINE = InnoDB AUTO_INCREMENT = 10635 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tos_rules
-- ----------------------------
DROP TABLE IF EXISTS `tos_rules`;
CREATE TABLE `tos_rules`  (
  `id` int(11) NOT NULL,
  `idx_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则名称',
  `idx_code` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '规则代码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除字段，0表示没被删除，1表示已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
