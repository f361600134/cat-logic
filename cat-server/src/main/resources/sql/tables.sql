/*
 Navicat Premium Data Transfer

 Source Server         : Jeremy
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : 139.9.44.104:3306
 Source Schema         : coral

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 21/03/2021 21:19:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for artifact
-- ----------------------------
DROP TABLE IF EXISTS `artifact`;
CREATE TABLE `artifact`  (
  `playerId` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '玩家ID',
  `configId` int NOT NULL COMMENT '神器配置id',
  `level` int NOT NULL COMMENT '神器等级',
  `exp` int NOT NULL COMMENT '经验',
  `refineLv` int NOT NULL COMMENT '精炼等级',
  `skillLv` int NOT NULL COMMENT '技能等级',
  `state` int NOT NULL COMMENT '状态',
  `holySealLv` int NOT NULL COMMENT '圣印等级',
  `skinId` int NOT NULL COMMENT '皮肤id',
  `missionStr` int NOT NULL COMMENT '已完成任务列表',
  `usedMaterialStr` int NOT NULL COMMENT '累计使用材料',
  PRIMARY KEY (`playerId`, `configId`) USING BTREE,
  INDEX `playerArtifact`(`playerId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of artifact
-- ----------------------------

-- ----------------------------
-- Table structure for chat_data
-- ----------------------------
DROP TABLE IF EXISTS `chat_data`;
CREATE TABLE `chat_data`  (
  `leftKey` bigint NOT NULL COMMENT '左key，与右key组合成唯一key',
  `rightKey` bigint NOT NULL COMMENT '右key，与左key组合成唯一key',
  `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '玩家聊天数据',
  `channel` int NOT NULL COMMENT '频道',
  `readTime` bigint NOT NULL COMMENT '读取聊天时间',
  UNIQUE INDEX `chat_key`(`leftKey`, `rightKey`) USING BTREE COMMENT '聊天索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chat_data
-- ----------------------------

-- ----------------------------
-- Table structure for chat_record
-- ----------------------------
DROP TABLE IF EXISTS `chat_record`;
CREATE TABLE `chat_record`  (
  `uniqueId` bigint NOT NULL COMMENT '唯一id',
  `channel` int NOT NULL COMMENT '频道号',
  `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '私聊数据',
  PRIMARY KEY (`uniqueId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '聊天记录, 共享数据, 私聊,世界聊天都需要记录以供玩家查询' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chat_record
-- ----------------------------

-- ----------------------------
-- Table structure for hero
-- ----------------------------
DROP TABLE IF EXISTS `hero`;
CREATE TABLE `hero`  (
  `id` bigint NOT NULL COMMENT '武将唯一id',
  `playerId` bigint NOT NULL COMMENT '所属玩家id',
  `configId` int NOT NULL COMMENT '配置id',
  `level` int NOT NULL COMMENT '等级',
  `starIdStr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '星格信息,List',
  `talent` int NULL DEFAULT NULL COMMENT '已激活天赋',
  `usedMaterialStr` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '记录使用材料',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `hero_key`(`playerId`, `configId`) USING BTREE COMMENT '玩家英雄索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hero
-- ----------------------------
INSERT INTO `hero` VALUES (541426506304327680, 1, 90001, 0, '[]', 0, '{}');

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `playerId` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '玩家ID',
  `itemId` bigint UNSIGNED NOT NULL COMMENT '道具唯一id',
  `configId` int NOT NULL COMMENT '道具配置id',
  `count` int NOT NULL COMMENT '物品数量',
  `recieveTime` int NOT NULL COMMENT '获得时间',
  PRIMARY KEY (`itemId`) USING BTREE,
  INDEX `playerItem`(`playerId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------

-- ----------------------------
-- Table structure for main_mission
-- ----------------------------
DROP TABLE IF EXISTS `main_mission`;
CREATE TABLE `main_mission`  (
  `playerId` bigint NULL DEFAULT 0 COMMENT '玩家id',
  `missionStr` varchar(15000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务数据str'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '主线任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of main_mission
-- ----------------------------

-- ----------------------------
-- Table structure for mission
-- ----------------------------
DROP TABLE IF EXISTS `mission`;
CREATE TABLE `mission`  (
  `configId` int NOT NULL COMMENT '任务ID',
  `playerId` int NOT NULL COMMENT '角色ID',
  `state` tinyint NOT NULL COMMENT '任务状态:0=未接取;1=已接取未完成;2=已完成未领取;3=已完成已领取',
  `progress` smallint NOT NULL COMMENT '任务进度',
  `recvTime` bigint NOT NULL COMMENT '任务接取时间',
  PRIMARY KEY (`playerId`, `configId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mission
-- ----------------------------

-- ----------------------------
-- Table structure for rank
-- ----------------------------
DROP TABLE IF EXISTS `rank`;
CREATE TABLE `rank`  (
  `curServerId` int NOT NULL DEFAULT 0 COMMENT '当前服务器Id',
  `rankType` int NOT NULL COMMENT '排行榜类型',
  `uniqueId` bigint NOT NULL COMMENT '对象id',
  `firstValue` bigint NOT NULL COMMENT '第一比较值,最主要的比较值',
  `secondValue` bigint NOT NULL COMMENT '第二比较值,第一比较值相同的情况下, 比较此值',
  `thirdValue` bigint NOT NULL COMMENT '第三比较值,第二比较值相同的情况下, 比较此值',
  `createTime` bigint NOT NULL COMMENT '入榜时间',
  INDEX `rankServer`(`curServerId`, `rankType`, `uniqueId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统排行榜' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rank
-- ----------------------------
INSERT INTO `rank` VALUES (1, 1, 1, 1, 0, 0, 0);
INSERT INTO `rank` VALUES (1, 1, 4, 4, 0, 0, 0);
INSERT INTO `rank` VALUES (1, 1, 3, 3, 0, 0, 0);
INSERT INTO `rank` VALUES (1, 1, 2, 2, 0, 0, 0);

-- ----------------------------
-- Table structure for stu
-- ----------------------------
DROP TABLE IF EXISTS `stu`;
CREATE TABLE `stu`  (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `birthday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu
-- ----------------------------
INSERT INTO `stu` VALUES (1, '1', 1, '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `birthday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `player`;
CREATE TABLE IF NOT EXISTS `player` (
  `playerId` int(11) NOT NULL COMMENT '角色ID',
  `accountName` varchar(64) NOT NULL COMMENT '角色账号,服务器生成的唯一账号,全服唯一',
  `inputName` varchar(64) NOT NULL COMMENT '玩家输入账号,记录下来用于排查玩家信息,玩家输入账号',
  `nickName` varchar(20) NOT NULL COMMENT '角色昵称,游戏内角色自定义名称,全服唯一',
  `channel` int(11) NOT NULL DEFAULT '0' COMMENT '渠道Id',
  `regTime` datetime NOT NULL COMMENT '注册时间',
  `lastTime` datetime NOT NULL COMMENT '最后登陆时间',
  `curServerId` int(11) NOT NULL DEFAULT '0' COMMENT '当前服务器Id',
  `initServerId` int(11) NOT NULL DEFAULT '0' COMMENT '玩家创角时的,最原始服务器Id',
  `level` smallint(6) NOT NULL DEFAULT '1' COMMENT '等级',
  `exp` int(11) NOT NULL DEFAULT '0' COMMENT '经验',
  `vip` smallint(6) NOT NULL DEFAULT '0' COMMENT 'VIP等级',
  `vipExp` int(6) NOT NULL DEFAULT '0' COMMENT 'VIP经验',
  `status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '角色状态,0:默认,1:禁言,2:封号,4:删号,8:其他',
  PRIMARY KEY (`playerId`),
  UNIQUE KEY `playerNickName` (`nickName`, `initServerId`),
  KEY `playerAccountName` (`accountName`, `initServerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

