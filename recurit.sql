/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : recurit

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 23/05/2022 22:58:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'NULL' COMMENT '用户名',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` date NULL DEFAULT NULL COMMENT '更新时间',
  `userface` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, '系统管理员', '13760611606', '$2a$10$bvSdrSOyw6GwnsD5kaDalupkwUtp02X3Hh861UCumYzSNwRoncnGW', '2022-01-01', NULL, 'https://c-ssl.duitang.com/uploads/item/202004/20/20200420104627_RKhyX.jpeg', NULL);
INSERT INTO `admin` VALUES (3, '客服02', '12345', '$2a$10$356h8/OrnT14C1IdE4Kr3.NhXHU0hMj0xNYP69PwSY3M0c/SlTzj.', '2022-04-17', '2022-04-17', NULL, '验证公司');
INSERT INTO `admin` VALUES (4, '客服01', '18900', '$2a$10$th2jUiVQD99PdBXISh0t.uijJntmjq.kwWG8tCFvFx84ij0RMHZha', '2022-04-19', NULL, NULL, '客服人员');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `adminId` bigint(20) NULL DEFAULT NULL COMMENT '管理员id',
  `rid` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  INDEX `adminId`(`adminId`) USING BTREE,
  CONSTRAINT `admin_role_ibfk_1` FOREIGN KEY (`adminId`) REFERENCES `admin` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `admin_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员-角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES (1, 1, 1);
INSERT INTO `admin_role` VALUES (3, 3, 2);
INSERT INTO `admin_role` VALUES (4, 4, 2);

-- ----------------------------
-- Table structure for adminmailbox
-- ----------------------------
DROP TABLE IF EXISTS `adminmailbox`;
CREATE TABLE `adminmailbox`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `sender` bigint(20) NULL DEFAULT NULL COMMENT '发件人id',
  `sendType` tinyint(4) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '发件人类型（1企业 2工人）',
  `createTime` date NULL DEFAULT NULL COMMENT '创建时间',
  `sendName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发件人名',
  `topic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别',
  `state` tinyint(4) NULL DEFAULT 0 COMMENT '状态（0未读 1已读）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of adminmailbox
-- ----------------------------
INSERT INTO `adminmailbox` VALUES (1, 11, 0001, '2022-04-23', '叮叮当当建筑有限公司', '有问题', '呜呜呜呜', NULL, 1);
INSERT INTO `adminmailbox` VALUES (2, 1, 0002, '2022-04-23', '李四', '提交bug', '没有bug的bug\n', NULL, 0);

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'NULL' COMMENT '公司名',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话号',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司地址',
  `license` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司执照',
  `person` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '法人',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` date NULL DEFAULT NULL COMMENT '更新时间',
  `userface` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司简述',
  `state` tinyint(4) NULL DEFAULT 1 COMMENT '启用状态（0 未通过 1待验证 2启用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '企业用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES (1, '精诚装修有限公司', '$2a$10$rV8G3vBW.XvBUS00wGfg0.hfBaPM5QS9ay7iJtLa0PldrMnDrHh5S', '13760611606', '广东省广州市白云区三元里大道一号', 'http://localhost:8081/myimg/7057c01ade4f43bfaeb1ad3943a6ebad.png', '张三', '526528786@qq.com', '2022-04-04', '2022-05-03', 'https://c-ssl.duitang.com/uploads/item/202004/20/20200420104627_RKhyX.jpeg', '展示使用!!', 2);
INSERT INTO `company` VALUES (2, '地利建筑公司', '$2a$10$inJQDGRx3hR2ovLWBIGHLeHxJb7WQRaIhCqonacmvntkg2DtAi.iC', '13890922909', '广东省广州市荔湾区老城里三号', NULL, '王五', NULL, '2020-10-11', '2022-04-08', NULL, '', 2);
INSERT INTO `company` VALUES (3, '武力建筑公司', '$2a$10$inJQDGRx3hR2ovLWBIGHLeHxJb7WQRaIhCqonacmvntkg2DtAi.iC', '13889898999', '广东省广州市从化区旗杆村12号', NULL, '张柳', NULL, '2022-02-21', '2022-04-19', NULL, '', 2);
INSERT INTO `company` VALUES (4, '明明建筑公司', '$2a$10$inJQDGRx3hR2ovLWBIGHLeHxJb7WQRaIhCqonacmvntkg2DtAi.iC', '13268686888', '广东省茂名市杏子村8号', NULL, '九七', NULL, '2022-01-30', '2022-04-09', NULL, '', 1);
INSERT INTO `company` VALUES (5, '彭喷建筑公司', '$2a$10$inJQDGRx3hR2ovLWBIGHLeHxJb7WQRaIhCqonacmvntkg2DtAi.iC', '13290909011', '广东省\r\n江门市江海区东海路康城广场', NULL, '流火', NULL, '2021-04-11', '2022-04-08', NULL, '', 2);
INSERT INTO `company` VALUES (6, '溜溜建筑公司', '$2a$10$93VZivJEb1XF7DFK6kGuaeDl87o097nUBboBY18WqMGsIlhbba7aC', '13290909899', '广东省\r\n江门市新会区S272', NULL, '张武', NULL, '2022-04-11', '2022-04-11', NULL, '', 1);
INSERT INTO `company` VALUES (7, '拼货建筑公司', '$2a$10$zDCWkP3MHdqmhcvRQj4OteJNSuZbxEu/dPdUVwb2.bCC1tIQB.vaS', '13290989898', '广东省清远市清城区沙湖村', NULL, '欧鸿', NULL, '2022-04-11', '2022-04-19', NULL, '', 2);
INSERT INTO `company` VALUES (8, '毗邻建筑公司', '$2a$10$drByV1Pm/sa/bJKsTZqe2eYn2iptf42sGJeeTd3A3FDHJXyXKFupK', '13290989811', '广东省广州市天河区', NULL, '珀丽', NULL, '2022-04-11', '2022-04-06', NULL, '', 0);
INSERT INTO `company` VALUES (9, '日托建筑公司', '$2a$10$jxiUz0/siT.ZqAtoqXsaUOkO9xKWVOjDEDFasppQRK9i.b4L3VEbm', '13290989822', '广东省广州市越秀区', NULL, '李书文', NULL, '2022-04-12', '2022-04-16', NULL, '', 0);
INSERT INTO `company` VALUES (10, '物贸建筑股份有限公司', '$2a$10$WTzWdHw33Od5hxuiRnXsYe9NglGGxVt4iBlR63vkQZ3zrPMevHEae', '13826891811', '广东省广州市白云区', '', '李贺', NULL, '2022-04-12', '2022-04-17', NULL, '小小公司', 2);
INSERT INTO `company` VALUES (11, '叮叮当当建筑有限公司', '$2a$10$.7L0dEBE4yh6ZPqAlMaAf.WseA/SHnpUvdUe2uiFTs27vWAOLZTPi', '18675059247', '广东省江门市蓬江区', 'http://localhost:8081/myimg/e09070fa301249b2aef23954d5260b1f.png', '鼎党', NULL, '2022-04-21', '2022-04-21', NULL, '', 1);
INSERT INTO `company` VALUES (12, 'aaa', '$2a$10$G8g0R/GjLjk128B23UeBb.Kq7D5wdgIR6jKWF03h0mFe3C571dKwG', '13760626891', 'aaa', 'http://localhost:8081/myimg/643ef61dad6749f79398763a41625fa0.png', 'aa', '526528786@qq.com', '2022-05-01', '2022-05-01', NULL, NULL, 1);
INSERT INTO `company` VALUES (13, '宏利建筑有限果实', '$2a$10$XgqWxkBkIuTyeeJ0uMf4UO.mf97O7nfmM5hKhiXtQzee5N6BG/8Om', '13826040068', '广东省广州市白云区西槎路鹅掌坦18号', 'http://localhost:8081/myimg/b2e293e06cf04fe5a81afcb6494d087a.png', '宏腾', '804466902@qq.com', '2022-05-02', '2022-05-02', NULL, '', 1);

-- ----------------------------
-- Table structure for company_role
-- ----------------------------
DROP TABLE IF EXISTS `company_role`;
CREATE TABLE `company_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `adminId` bigint(20) NULL DEFAULT NULL COMMENT '企业id',
  `rid` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  INDEX `adminId`(`adminId`) USING BTREE,
  CONSTRAINT `company_role_ibfk_1` FOREIGN KEY (`adminId`) REFERENCES `company` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `company_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '企业-角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company_role
-- ----------------------------
INSERT INTO `company_role` VALUES (1, 1, 5);
INSERT INTO `company_role` VALUES (2, 2, 4);
INSERT INTO `company_role` VALUES (3, 3, 5);
INSERT INTO `company_role` VALUES (4, 5, 4);
INSERT INTO `company_role` VALUES (5, 7, 5);
INSERT INTO `company_role` VALUES (6, 8, 4);
INSERT INTO `company_role` VALUES (7, 9, 4);
INSERT INTO `company_role` VALUES (8, 10, 5);
INSERT INTO `company_role` VALUES (9, 11, 4);
INSERT INTO `company_role` VALUES (10, 12, 4);
INSERT INTO `company_role` VALUES (11, 13, 4);

-- ----------------------------
-- Table structure for company_site
-- ----------------------------
DROP TABLE IF EXISTS `company_site`;
CREATE TABLE `company_site`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `companyId` bigint(20) NULL DEFAULT NULL COMMENT '企业id',
  `sid` bigint(20) NULL DEFAULT NULL COMMENT '工地id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `companyId`(`companyId`) USING BTREE,
  INDEX `sid`(`sid`) USING BTREE,
  CONSTRAINT `company_site_ibfk_1` FOREIGN KEY (`companyId`) REFERENCES `company` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `company_site_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `site` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '企业-工地表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company_site
-- ----------------------------
INSERT INTO `company_site` VALUES (1, 1, 1);
INSERT INTO `company_site` VALUES (2, 1, 2);
INSERT INTO `company_site` VALUES (3, 1, 3);
INSERT INTO `company_site` VALUES (4, 1, 4);
INSERT INTO `company_site` VALUES (5, 1, 5);

-- ----------------------------
-- Table structure for companymailbox
-- ----------------------------
DROP TABLE IF EXISTS `companymailbox`;
CREATE TABLE `companymailbox`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sender` int(2) NULL DEFAULT NULL COMMENT '发件人id',
  `receiver` int(2) NULL DEFAULT NULL COMMENT '接收人id',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `topic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `siteId` bigint(20) NULL DEFAULT NULL COMMENT '工地id',
  `worktypeId` bigint(4) NULL DEFAULT NULL COMMENT '工种id',
  `type` tinyint(4) NULL DEFAULT 0 COMMENT '类别（0普通 1邀请 2回信）',
  `state` tinyint(4) NULL DEFAULT 0 COMMENT '状态（0未读 1已读 ）',
  `comdflag` tinyint(4) NULL DEFAULT 1 COMMENT '公司删除标注（1为未删除 0 为删除）',
  `workdflag` tinyint(4) NULL DEFAULT 1 COMMENT '工人删除标注（1为未删除 0为删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '企业邮箱' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of companymailbox
-- ----------------------------
INSERT INTO `companymailbox` VALUES (1, 1, 1, '2022-04-18', '面谈', '请问是否有意愿与我司面谈呢？', 4, 16, 1, 1, 1, 0);
INSERT INTO `companymailbox` VALUES (2, 1, 2, '2022-04-18', '邀请', '邀请您应聘该工地', 5, NULL, 1, 0, 1, 1);
INSERT INTO `companymailbox` VALUES (4, 1, 1, '2022-04-19', '邀请', '邀请您应聘工地', 5, 0, 1, 1, 1, 1);
INSERT INTO `companymailbox` VALUES (5, 1, 11, '2022-04-19', '邀请面谈', '邀请您面谈', 4, 17, 1, 0, 1, 1);
INSERT INTO `companymailbox` VALUES (7, 1, 6, '2022-04-19', '邀请面谈', '请问您有意愿面谈嘛', 4, 4, 1, 0, 1, 1);
INSERT INTO `companymailbox` VALUES (8, 1, 7, '2022-04-19', '来玩', '来我这玩', 0, 0, 0, 1, 0, 1);
INSERT INTO `companymailbox` VALUES (10, 1, 13, '2022-04-19', '邀请', '邀请您参加面谈', 4, 12, 1, 0, 1, 1);
INSERT INTO `companymailbox` VALUES (12, 1, 1, '2022-04-20', '不收', '我们不收搬砖的', 0, 0, 2, 0, 1, 1);
INSERT INTO `companymailbox` VALUES (13, 1, 1, '2022-04-20', '请勿骚扰', '请不要骚扰我司', 0, 0, 2, 0, 1, 1);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'url',
  `path` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'path',
  `component` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `iconCls` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `keepAlive` tinyint(1) NULL DEFAULT NULL COMMENT '是否保持激活',
  `requireAuth` tinyint(1) NULL DEFAULT NULL COMMENT '是否要求权限',
  `parentId` bigint(20) NULL DEFAULT NULL COMMENT '父id',
  `enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parentId`(`parentId`) USING BTREE,
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `mid` bigint(20) NULL DEFAULT NULL COMMENT '菜单id',
  `rid` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `mid`(`mid`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  CONSTRAINT `menu_role_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `menu_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限-角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_role
-- ----------------------------

-- ----------------------------
-- Table structure for recurit_message
-- ----------------------------
DROP TABLE IF EXISTS `recurit_message`;
CREATE TABLE `recurit_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` int(20) NULL DEFAULT 1 COMMENT '招聘人数',
  `salary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '薪资',
  `stype_id` int(2) NULL DEFAULT NULL COMMENT '薪资种类ID',
  `wtype_id` int(2) NULL DEFAULT NULL COMMENT '工种ID',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '需求简述',
  `state` tinyint(4) NULL DEFAULT 0 COMMENT '招聘状态（1招聘中 0不招）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '招聘信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recurit_message
-- ----------------------------
INSERT INTO `recurit_message` VALUES (1, 10, '5000', 2, 4, '2022-04-13', NULL, 1);
INSERT INTO `recurit_message` VALUES (3, 2, '300', 1, 12, '2022-04-13', NULL, 1);
INSERT INTO `recurit_message` VALUES (4, 2, '10000', 4, 10, '2022-04-13', NULL, 1);
INSERT INTO `recurit_message` VALUES (7, 1, '8000', 2, 7, '2022-04-14', NULL, 1);
INSERT INTO `recurit_message` VALUES (10, 12, '8000', 2, 7, '2022-04-14', NULL, 1);
INSERT INTO `recurit_message` VALUES (16, 1, '123', 1, 2, '2022-04-14', NULL, 1);
INSERT INTO `recurit_message` VALUES (17, 1, '21', 1, 5, '2022-04-14', NULL, 1);
INSERT INTO `recurit_message` VALUES (18, 2, '6000', 2, 12, '2022-04-16', NULL, 1);
INSERT INTO `recurit_message` VALUES (19, 3, '30', 3, 6, '2022-04-16', NULL, 1);
INSERT INTO `recurit_message` VALUES (20, 2, '30', 3, 11, '2022-04-19', NULL, 1);

-- ----------------------------
-- Table structure for recurit_message_worker
-- ----------------------------
DROP TABLE IF EXISTS `recurit_message_worker`;
CREATE TABLE `recurit_message_worker`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `workerId` bigint(20) NULL DEFAULT NULL COMMENT '工人id',
  `rid` bigint(20) NULL DEFAULT NULL COMMENT '招聘信息id',
  `createTime` date NULL DEFAULT NULL COMMENT '应聘时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `workerId`(`workerId`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  CONSTRAINT `recurit_message_worker_message_ibfk_1` FOREIGN KEY (`workerId`) REFERENCES `worker` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `recurit_message_worker_message_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `recurit_message` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '招聘信息-工人表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recurit_message_worker
-- ----------------------------
INSERT INTO `recurit_message_worker` VALUES (1, 1, 1, '2022-04-14');
INSERT INTO `recurit_message_worker` VALUES (2, 6, 1, '2022-04-12');
INSERT INTO `recurit_message_worker` VALUES (3, 6, 3, '2022-04-14');
INSERT INTO `recurit_message_worker` VALUES (4, 6, 4, '2022-04-13');
INSERT INTO `recurit_message_worker` VALUES (5, 1, 4, '2022-04-12');
INSERT INTO `recurit_message_worker` VALUES (6, 1, 17, '2022-04-16');
INSERT INTO `recurit_message_worker` VALUES (7, 1, 16, '2022-04-16');
INSERT INTO `recurit_message_worker` VALUES (8, 1, 3, '2022-04-16');
INSERT INTO `recurit_message_worker` VALUES (9, 11, 10, '2022-04-19');
INSERT INTO `recurit_message_worker` VALUES (10, 11, 3, '2022-04-19');
INSERT INTO `recurit_message_worker` VALUES (11, 11, 17, '2022-04-19');
INSERT INTO `recurit_message_worker` VALUES (14, 13, 3, '2022-04-19');
INSERT INTO `recurit_message_worker` VALUES (15, 1, 10, '2022-04-19');
INSERT INTO `recurit_message_worker` VALUES (17, 8, 10, '2022-04-20');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `nameZh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_admin', '系统管理员', NULL);
INSERT INTO `role` VALUES (2, 'ROLE_admin_service', '客服人员', '验证企业用户的');
INSERT INTO `role` VALUES (3, 'ROLE_worker', '工人', NULL);
INSERT INTO `role` VALUES (4, 'ROLE_company_not', '公司（未验证）', '未验证的公司角色，不能发布工地');
INSERT INTO `role` VALUES (5, 'ROLE_company', '公司', '通过验证的公司');

-- ----------------------------
-- Table structure for salary_type
-- ----------------------------
DROP TABLE IF EXISTS `salary_type`;
CREATE TABLE `salary_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '种类名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '薪资种类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of salary_type
-- ----------------------------
INSERT INTO `salary_type` VALUES (1, '日薪');
INSERT INTO `salary_type` VALUES (2, '月薪');
INSERT INTO `salary_type` VALUES (3, '时薪');
INSERT INTO `salary_type` VALUES (4, '固定月薪');
INSERT INTO `salary_type` VALUES (5, '计件');
INSERT INTO `salary_type` VALUES (6, '年薪');

-- ----------------------------
-- Table structure for site
-- ----------------------------
DROP TABLE IF EXISTS `site`;
CREATE TABLE `site`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工地名',
  `user_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工地负责人',
  `phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工地地址',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `start_time` date NULL DEFAULT NULL COMMENT '开工时间',
  `complete_time` date NULL DEFAULT NULL COMMENT '完工时间',
  `expect_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预计工期',
  `userface` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工地简述',
  `recurit_state` tinyint(4) NULL DEFAULT 0 COMMENT '招聘状态（1招聘中 0不招）',
  `site_state` tinyint(4) NULL DEFAULT 0 COMMENT '工地状态（1施工中 0完工）',
  `dflag` tinyint(4) UNSIGNED NULL DEFAULT 1 COMMENT '工地删除(0删除 1未删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '工地' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of site
-- ----------------------------
INSERT INTO `site` VALUES (1, '白云西北2号工地', '丽丽', '12388811888', '广东省广州市白云区西北2号路', '2022-04-13', '2022-04-20', NULL, NULL, NULL, NULL, 1, 1, 1);
INSERT INTO `site` VALUES (2, '恒河开发', '柳大侠', '12388811228', '印度恒河岸边', '2022-04-13', '2022-04-20', '2022-04-27', '2年', NULL, NULL, 0, 0, 1);
INSERT INTO `site` VALUES (3, '阿斯蒂芬', '黎科佑', '12388877788', '广东省广州市白云区', '2022-04-13', '2022-04-23', NULL, '3个月', NULL, NULL, 1, 1, 1);
INSERT INTO `site` VALUES (4, '蓬江契合工地', '李亚伟', '13722288222', '广东省江门市蓬江区契合十号', '2022-04-13', '2022-04-28', NULL, '2年', NULL, '长期工地', 1, 1, 1);
INSERT INTO `site` VALUES (5, '里屋蒲工地', '奥斯蒙特拉', '13722999222', '广东省佛山市区里二号', '2022-04-16', '2022-04-20', '2022-04-27', '3个月', NULL, NULL, 1, 1, 1);

-- ----------------------------
-- Table structure for site_recurit_message
-- ----------------------------
DROP TABLE IF EXISTS `site_recurit_message`;
CREATE TABLE `site_recurit_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `siteId` bigint(20) NULL DEFAULT NULL COMMENT '工地id',
  `rid` bigint(20) NULL DEFAULT NULL COMMENT '招聘信息id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `siteId`(`siteId`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  CONSTRAINT `site_recurit_message_ibfk_1` FOREIGN KEY (`siteId`) REFERENCES `site` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `site_recurit_message_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `recurit_message` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '工地-招聘信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of site_recurit_message
-- ----------------------------
INSERT INTO `site_recurit_message` VALUES (2, 4, 3);
INSERT INTO `site_recurit_message` VALUES (3, 4, 4);
INSERT INTO `site_recurit_message` VALUES (6, 4, 7);
INSERT INTO `site_recurit_message` VALUES (15, 4, 16);
INSERT INTO `site_recurit_message` VALUES (16, 4, 17);
INSERT INTO `site_recurit_message` VALUES (17, 3, 1);
INSERT INTO `site_recurit_message` VALUES (21, 3, 10);
INSERT INTO `site_recurit_message` VALUES (22, 5, 18);
INSERT INTO `site_recurit_message` VALUES (23, 5, 19);
INSERT INTO `site_recurit_message` VALUES (24, 5, 20);

-- ----------------------------
-- Table structure for sysmessage
-- ----------------------------
DROP TABLE IF EXISTS `sysmessage`;
CREATE TABLE `sysmessage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `createTime` date NULL DEFAULT NULL COMMENT '创建时间',
  `state` tinyint(4) NULL DEFAULT NULL,
  `type` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sysmessage
-- ----------------------------
INSERT INTO `sysmessage` VALUES (2, '欢迎使用', '欢迎各位使用建筑工地员工招聘系统!!!', '2022-05-03', NULL, NULL);
INSERT INTO `sysmessage` VALUES (3, '有事反馈', '有事反馈请使用右上角“提交反馈”或致电：020-13760611606', '2022-04-23', NULL, NULL);

-- ----------------------------
-- Table structure for work_type
-- ----------------------------
DROP TABLE IF EXISTS `work_type`;
CREATE TABLE `work_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '种类名',
  `state` tinyint(4) NULL DEFAULT 1 COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '工种类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of work_type
-- ----------------------------
INSERT INTO `work_type` VALUES (1, '手工木工', 1);
INSERT INTO `work_type` VALUES (2, '精细木工', 1);
INSERT INTO `work_type` VALUES (3, '架子工', 1);
INSERT INTO `work_type` VALUES (4, '钢筋工', 1);
INSERT INTO `work_type` VALUES (5, '抹灰工', 1);
INSERT INTO `work_type` VALUES (6, '油漆工', 1);
INSERT INTO `work_type` VALUES (7, '混凝土工', 1);
INSERT INTO `work_type` VALUES (8, '砌筑工', 1);
INSERT INTO `work_type` VALUES (9, '防水工', 1);
INSERT INTO `work_type` VALUES (10, '电工', 1);
INSERT INTO `work_type` VALUES (11, '电焊工', 1);
INSERT INTO `work_type` VALUES (12, '管工', 1);
INSERT INTO `work_type` VALUES (13, '模板工', 1);
INSERT INTO `work_type` VALUES (14, '安装钳工', 1);
INSERT INTO `work_type` VALUES (15, '安装起重工', 1);
INSERT INTO `work_type` VALUES (16, '管道工', 1);
INSERT INTO `work_type` VALUES (17, '通风工', 1);
INSERT INTO `work_type` VALUES (18, '水质检验工', 1);
INSERT INTO `work_type` VALUES (19, '机泵运行工', 1);
INSERT INTO `work_type` VALUES (20, '水井工', 1);
INSERT INTO `work_type` VALUES (21, '水表装修工', 1);
INSERT INTO `work_type` VALUES (22, '供水工', 1);
INSERT INTO `work_type` VALUES (23, '筑路工', 1);
INSERT INTO `work_type` VALUES (24, '下水道工', 1);
INSERT INTO `work_type` VALUES (25, '沥青工', 1);
INSERT INTO `work_type` VALUES (28, '植保工', 1);
INSERT INTO `work_type` VALUES (29, '污水清理工', 1);

-- ----------------------------
-- Table structure for worker
-- ----------------------------
DROP TABLE IF EXISTS `worker`;
CREATE TABLE `worker`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'NULL' COMMENT '姓名',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话号',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `nowAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '常驻地址',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作意向地址',
  `education` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学历',
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '出生年月',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` date NULL DEFAULT NULL COMMENT '更新时间',
  `userface` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人简述',
  `state` tinyint(4) NULL DEFAULT 0 COMMENT '状态（1 无职业 0已入职 0暂不找工）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '工人用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of worker
-- ----------------------------
INSERT INTO `worker` VALUES (1, '李四', '$2a$10$nEI/Dtb0U8v7btUu1Rfz9ueMXejFTXzD6xZLCtN.bSjoUHDCuvG2a', '13760611606', '5522@qq.com', '广东省广州市从化区', '广东省广州市白云区鹅掌坦二号', '本科', '男', '2022-04-04', '2022-04-04', '2022-04-04', 'https://c-ssl.duitang.com/uploads/item/202004/20/20200420104627_RKhyX.jpeg', '测试使用', 1);
INSERT INTO `worker` VALUES (2, '五名', '$2a$10$JJWBRu8Z7F4hnHW9lUwyFewzkiO11RQm5TpNTCekP5RG77vUCok7q', '13788822888', '3399@163.com', '广东省东莞市', '广东省广州市', '本科', '男', '1998-10-10', '2022-04-12', NULL, NULL, NULL, 1);
INSERT INTO `worker` VALUES (6, '五名', '$2a$10$UfvXLj3XGttNBOkAMeNbAOuc37MttKdzatSLtnL9XoKwGniHJFWIW', '13788822666', '339889@163.com', '广东省广州市番禺区', '广东省广州市', '本科', '男', '1998-10-10', '2022-04-12', NULL, NULL, NULL, 1);
INSERT INTO `worker` VALUES (7, '朋友', '$2a$10$G7001UsBhc6pzCFjuNqYHehtNLu4dcBljFB/t2FEdYV2jaXyCgFuW', '13788822555', '22299@163.com', '广东省广州市荔湾区', '广东省广州市', '本科', '男', '1998-10-10', '2022-04-12', NULL, NULL, NULL, 1);
INSERT INTO `worker` VALUES (8, '麒麟', '$2a$10$ps5AZ1WN5NODMt1TY4wUU.nQS2RibVJKz6ESP8Od0Awh4zV.qnCGC', '13788822333', '3393329@163.com', '广东省中山市', '广东省广州市', '本科', '男', '1999-10-10', '2022-04-12', NULL, NULL, NULL, 1);
INSERT INTO `worker` VALUES (9, '艾莉', '$2a$10$pCairkLWFsqEArDPlHfnrewaKtfZ5yZQkHfVsq7BWElvE7gopQf1u', '12866889944', '123789@163.com', '广东省汕头市', '广东省广州市白云区', '本科', '男', '2022-04-01', '2022-04-12', NULL, NULL, '', 1);
INSERT INTO `worker` VALUES (10, '阪琉璃', '$2a$10$LjMxM/Rzy10jaHEAMDpZge12HkdmKILR5rj2eXJQk6jswYOs.uf4O', '13788999888', '88990012@163.com', '广东省江门市蓬江区七子巷口', '广东省广州市白云区', '本科', '女', '2022-04-15', '2022-04-14', '2022-04-06', NULL, '', 0);
INSERT INTO `worker` VALUES (11, '胡莉', '$2a$10$4J07k7epKYoegsGQnUT.2OOIeAGlEHedfOi9e.l8KNvGtiaultc8q', '13189445359', '2054220700@163.com', '广东省江门市蓬江区', '广东省江门市蓬江区', '本科', '女', '1998-04-10', '2022-04-19', NULL, NULL, '', 1);
INSERT INTO `worker` VALUES (13, '呼啦啦', '$2a$10$uMkBFASDY0FYyvs7B8LoeesvgWWUDc/dsJcgXevJO6tePCIYTXzfe', '13877711777', NULL, '广东省江门市蓬江区星光大道2号', '广东省江门市蓬江区', '本科', '男', '1998-03-05', '2022-04-19', NULL, NULL, '', 1);

-- ----------------------------
-- Table structure for worker_role
-- ----------------------------
DROP TABLE IF EXISTS `worker_role`;
CREATE TABLE `worker_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `adminId` bigint(20) NULL DEFAULT NULL COMMENT '工人id',
  `rid` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `rid`(`rid`) USING BTREE,
  INDEX `adminId`(`adminId`) USING BTREE,
  CONSTRAINT `worker_role_ibfk_1` FOREIGN KEY (`adminId`) REFERENCES `worker` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `worker_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '工人-角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of worker_role
-- ----------------------------
INSERT INTO `worker_role` VALUES (1, 1, 3);
INSERT INTO `worker_role` VALUES (2, 2, 3);
INSERT INTO `worker_role` VALUES (3, 6, 3);
INSERT INTO `worker_role` VALUES (4, 7, 3);
INSERT INTO `worker_role` VALUES (5, 8, 3);
INSERT INTO `worker_role` VALUES (6, 9, 3);
INSERT INTO `worker_role` VALUES (7, 10, 3);
INSERT INTO `worker_role` VALUES (8, 11, 3);
INSERT INTO `worker_role` VALUES (10, 13, 3);

-- ----------------------------
-- Table structure for worker_work_type
-- ----------------------------
DROP TABLE IF EXISTS `worker_work_type`;
CREATE TABLE `worker_work_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `workerId` bigint(20) NULL DEFAULT NULL COMMENT '工人id',
  `tid` bigint(20) NULL DEFAULT NULL COMMENT '工种id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `workerId`(`workerId`) USING BTREE,
  INDEX `tid`(`tid`) USING BTREE,
  CONSTRAINT `worker_work_type_message_ibfk_1` FOREIGN KEY (`workerId`) REFERENCES `worker` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `worker_work_type_message_ibfk_2` FOREIGN KEY (`tid`) REFERENCES `work_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '工人-工种表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of worker_work_type
-- ----------------------------
INSERT INTO `worker_work_type` VALUES (1, 6, 1);
INSERT INTO `worker_work_type` VALUES (2, 6, 2);
INSERT INTO `worker_work_type` VALUES (3, 8, 1);
INSERT INTO `worker_work_type` VALUES (4, 8, 2);
INSERT INTO `worker_work_type` VALUES (5, 9, 1);
INSERT INTO `worker_work_type` VALUES (6, 9, 3);
INSERT INTO `worker_work_type` VALUES (7, 9, 5);
INSERT INTO `worker_work_type` VALUES (9, 10, 1);
INSERT INTO `worker_work_type` VALUES (32, 11, 10);
INSERT INTO `worker_work_type` VALUES (35, 13, 1);
INSERT INTO `worker_work_type` VALUES (36, 13, 6);
INSERT INTO `worker_work_type` VALUES (37, 1, 1);
INSERT INTO `worker_work_type` VALUES (38, 1, 2);
INSERT INTO `worker_work_type` VALUES (39, 1, 3);

-- ----------------------------
-- Table structure for workermailbox
-- ----------------------------
DROP TABLE IF EXISTS `workermailbox`;
CREATE TABLE `workermailbox`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sender` int(2) NULL DEFAULT NULL COMMENT '发件人id',
  `receiver` int(2) NULL DEFAULT NULL COMMENT '接收人id',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `topic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `workerId` bigint(20) NULL DEFAULT NULL COMMENT '工人id',
  `siteId` bigint(20) NULL DEFAULT NULL COMMENT '工地id',
  `worktypeId` bigint(4) NULL DEFAULT NULL COMMENT '工种id',
  `type` tinyint(4) NULL DEFAULT 0 COMMENT '类别（0普通 1推荐 2回信）',
  `state` tinyint(4) NULL DEFAULT 0 COMMENT '状态（0未读 1已读）',
  `comdflag` tinyint(4) NULL DEFAULT 1 COMMENT '公司删除标注（1为未删除 0 为删除了）',
  `workdflag` tinyint(4) NULL DEFAULT 1 COMMENT '工人删除标注（1为未删除 0 为删除了）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '工人邮箱' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of workermailbox
-- ----------------------------
INSERT INTO `workermailbox` VALUES (1, 1, 1, '2022-04-19', '自荐信', '我是李四，希望与您公司面谈', 0, 0, 0, 0, 1, 1, 1);
INSERT INTO `workermailbox` VALUES (2, 1, 1, '2022-04-19', '自荐信', '我是李四，希望与您公司面谈', 0, 4, 12, 1, 1, 1, 1);
INSERT INTO `workermailbox` VALUES (3, 1, 1, '2022-04-19', '请问', '请问您司是否还招搬砖的呢', 1, 0, 0, 0, 1, 0, 1);
INSERT INTO `workermailbox` VALUES (4, 1, 1, '2022-04-20', '接收', '好的，我将考虑考虑', NULL, 0, 0, 2, 1, 1, 1);
INSERT INTO `workermailbox` VALUES (5, 1, 1, '2022-04-20', '愿意面谈', '好的，请问具体时间和地点', NULL, 0, 0, 2, 1, 1, 1);
INSERT INTO `workermailbox` VALUES (6, 7, 1, '2022-04-20', '忙', '忙,不玩!', NULL, 0, 0, 2, 1, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
