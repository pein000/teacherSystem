/*
Navicat MySQL Data Transfer

Source Server         : MyNative
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : t_system

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2017-02-28 16:34:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ts_ocr
-- ----------------------------
DROP TABLE IF EXISTS `ts_ocr`;
CREATE TABLE `ts_ocr` (
  `id` int(12) NOT NULL,
  `p_origin_path` varchar(255) DEFAULT NULL,
  `p_dest_path` varchar(255) DEFAULT NULL,
  `content` text,
  `user_id` int(12) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_ocr
-- ----------------------------

-- ----------------------------
-- Table structure for ts_user
-- ----------------------------
DROP TABLE IF EXISTS `ts_user`;
CREATE TABLE `ts_user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telphone` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_user
-- ----------------------------
