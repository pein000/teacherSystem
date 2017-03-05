/*
Navicat MySQL Data Transfer

Source Server         : MyNative
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : t_system

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2017-02-28 17:15:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ts_ocr
-- ----------------------------
DROP TABLE IF EXISTS `ts_ocr`;
CREATE TABLE `ts_ocr` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `p_origin_path` varchar(255) DEFAULT NULL,
  `p_dest_path` varchar(255) DEFAULT NULL,
  `content` text,
  `user_id` int(12) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_ocr
-- ----------------------------
INSERT INTO `ts_ocr` VALUES ('100001', 'D:\\picture\\t1.jpg', 'D:\\picture\\src4.jpg', null, null, '2017-02-28 17:14:29', '2017-02-28 17:14:32');

-- ----------------------------
-- Table structure for ts_user
-- ----------------------------
DROP TABLE IF EXISTS `ts_user`;
CREATE TABLE `ts_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telphone` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_user
-- ----------------------------
