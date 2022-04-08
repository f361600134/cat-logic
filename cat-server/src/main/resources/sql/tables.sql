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

 Date: 08/04/2022 11:49:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` int NOT NULL COMMENT '活动类型id',
  `status` int NULL DEFAULT 0 COMMENT '活动状态',
  `stage` int NULL DEFAULT 0 COMMENT '活动阶段',
  `pause` tinyint(1) NULL DEFAULT 0 COMMENT '是否暂停中',
  `configId` int NULL DEFAULT 0 COMMENT '配置id',
  `configType` int NULL DEFAULT 0 COMMENT '配置类型',
  `planId` int NULL DEFAULT 0 COMMENT '活动方案id',
  `prepareTime` bigint NULL DEFAULT NULL COMMENT '活动准备时间',
  `beginTime` bigint NULL DEFAULT 0 COMMENT '活动开始时间',
  `settleTime` bigint NULL DEFAULT 0 COMMENT '活动结算时间',
  `closeTime` bigint NULL DEFAULT 0 COMMENT '活动结束时间',
  `customData` longblob NULL COMMENT '活动的自定义数据',
  `bagData` longblob NULL COMMENT '活动背包缓存的数据',
  `openedNum` int NULL DEFAULT 0 COMMENT '开启的次数',
  `curServerId` int NULL DEFAULT NULL COMMENT '当前所处服务器id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '活动数据' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for activity_item
-- ----------------------------
DROP TABLE IF EXISTS `activity_item`;
CREATE TABLE `activity_item`  (
  `playerId` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '玩家ID',
  `itemId` bigint UNSIGNED NOT NULL COMMENT '道具唯一id',
  `configId` int NOT NULL COMMENT '道具配置id',
  `count` int NOT NULL COMMENT '物品数量',
  `recieveTime` bigint NOT NULL COMMENT '获得的时间戳',
  PRIMARY KEY (`itemId`) USING BTREE,
  INDEX `playerItem`(`playerId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '活动道具' ROW_FORMAT = Dynamic;

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
  `activeTime` bigint NOT NULL COMMENT '激活时间',
  `holySealLv` int NOT NULL COMMENT '圣印等级',
  `skinId` int NOT NULL COMMENT '皮肤id',
  `missionStr` int NOT NULL COMMENT '已完成任务列表',
  `usedMaterialStr` int NOT NULL COMMENT '累计使用材料',
  PRIMARY KEY (`playerId`, `configId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chat
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat`  (
  `leftKey` bigint NOT NULL COMMENT '左key，与右key组合成唯一key',
  `rightKey` bigint NOT NULL COMMENT '右key，与左key组合成唯一key',
  `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '玩家聊天数据',
  `channel` int NOT NULL COMMENT '频道',
  UNIQUE INDEX `chat_key`(`leftKey`, `rightKey`) USING BTREE COMMENT '聊天索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for family
-- ----------------------------
DROP TABLE IF EXISTS `family`;
CREATE TABLE `family`  (
  `id` bigint NOT NULL COMMENT '家族id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '家族名,可修改',
  `applyStr` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '申请列表',
  `memberStr` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '成员列表',
  `exp` int NULL DEFAULT NULL COMMENT '经验',
  `level` smallint NULL DEFAULT NULL COMMENT '等级',
  `createTime` bigint NULL DEFAULT NULL COMMENT '创建时间戳',
  `notice` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '公告,可修改',
  `initServerId` int NULL DEFAULT NULL COMMENT '初始服务器id',
  `curServerId` int NULL DEFAULT NULL COMMENT '当前服务器id',
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '家族号,可以修改.用于精确查询',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `family_name`(`name`) USING BTREE COMMENT '索引名字',
  INDEX `family_serverId`(`curServerId`) USING BTREE COMMENT '服务器id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for function_control
-- ----------------------------
DROP TABLE IF EXISTS `function_control`;
CREATE TABLE `function_control`  (
  `playerId` bigint NOT NULL COMMENT '玩家id',
  `functionId` int NOT NULL COMMENT '功能id',
  `resetTime` bigint NULL DEFAULT NULL COMMENT '重置时间',
  PRIMARY KEY (`playerId`, `functionId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_mail
-- ----------------------------
DROP TABLE IF EXISTS `group_mail`;
CREATE TABLE `group_mail`  (
  `id` bigint NOT NULL COMMENT '邮件唯一ID,本服唯一邮件id',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮件标题',
  `content` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮件内容',
  `rewards` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '奖励:{configId:num,configId:num}',
  `createTime` bigint NOT NULL COMMENT '邮件创建时间',
  `expireTime` bigint NOT NULL COMMENT '邮件过期时间',
  `extendStr` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '额外信息,包含已读,已领取玩家id列表',
  `curServerId` int NOT NULL DEFAULT 0 COMMENT '当前服务器Id',
  `initServerId` int NOT NULL DEFAULT 0 COMMENT '原始服务器Id',
  `backstageId` bigint NULL DEFAULT NULL COMMENT '后台生成的邮件id，用于合服时数据合并',
  `serverIdStr` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '邮件发送的服务器组',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `serverId`(`curServerId`) USING BTREE COMMENT '服务器id作为索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '玩家个人邮件表' ROW_FORMAT = Dynamic;

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
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `playerId` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '玩家ID',
  `itemId` bigint UNSIGNED NOT NULL COMMENT '道具唯一id',
  `configId` int NOT NULL COMMENT '道具配置id',
  `count` int NOT NULL COMMENT '物品数量',
  `recieveTime` bigint NOT NULL COMMENT '获得的时间戳',
  PRIMARY KEY (`itemId`) USING BTREE,
  INDEX `playerItem`(`playerId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for learn_community
-- ----------------------------
DROP TABLE IF EXISTS `learn_community`;
CREATE TABLE `learn_community`  (
  `todayExp` int NULL DEFAULT NULL COMMENT '今日获得经验',
  `exp` int NULL DEFAULT NULL COMMENT '总经验=每日任务经验+每周任务经验',
  `level` int NULL DEFAULT NULL COMMENT '等级',
  `exclusive` bit(1) NULL DEFAULT NULL COMMENT '是否购买了专属奖励',
  `rewardDataMapStr` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '奖励记录, 仅记录已领奖的配置',
  `questTypeDataStr` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '任务数据,跟随者活动,活动结束,任务关闭',
  `dailyActiveMapStr` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '活跃记录, 记录已领取的日活跃奖励',
  `playerId` bigint NOT NULL COMMENT '玩家id',
  `activityId` int NULL DEFAULT NULL COMMENT '活动id',
  `lastResetTime` bigint NULL DEFAULT NULL COMMENT '最后重置时间',
  PRIMARY KEY (`playerId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mail
-- ----------------------------
DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail`  (
  `id` bigint NOT NULL COMMENT '邮件ID',
  `playerId` bigint NOT NULL COMMENT '角色ID',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮件标题',
  `content` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮件内容',
  `rewards` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '奖励:{configId:num,configId:num}',
  `createTime` bigint NOT NULL COMMENT '邮件创建时间',
  `expireTime` bigint NOT NULL COMMENT '邮件过期时间',
  `state` tinyint NOT NULL DEFAULT 0 COMMENT '0=未读取;1=已读',
  PRIMARY KEY (`id`, `playerId`) USING BTREE,
  INDEX `playerId`(`playerId`) USING BTREE COMMENT '玩家id,作为索引查询'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '玩家个人邮件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for main_mission
-- ----------------------------
DROP TABLE IF EXISTS `main_mission`;
CREATE TABLE `main_mission`  (
  `playerId` bigint NOT NULL DEFAULT 0 COMMENT '玩家id',
  `missionStr` varchar(15000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务数据str',
  PRIMARY KEY (`playerId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '主线任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mission
-- ----------------------------
DROP TABLE IF EXISTS `mission`;
CREATE TABLE `mission`  (
  `playerId` bigint NOT NULL COMMENT '角色ID',
  `missionType` int NOT NULL COMMENT '任务类型,1:主线,2:支线,3:...',
  `missionStr` varchar(15000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务数据str',
  PRIMARY KEY (`playerId`, `missionType`) USING BTREE,
  INDEX `mission_key`(`playerId`) USING BTREE COMMENT '玩家id作为索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pet
-- ----------------------------
DROP TABLE IF EXISTS `pet`;
CREATE TABLE `pet`  (
  `configId` int NOT NULL COMMENT '配置id',
  `uniqueId` bigint NOT NULL COMMENT '唯一id',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '名字',
  `birthDate` bigint NULL DEFAULT NULL COMMENT '出生日期时间戳',
  `hungry` smallint NULL DEFAULT NULL COMMENT '饥饿点',
  `trust` smallint NULL DEFAULT NULL COMMENT '信任点',
  `aptitudeId` int NULL DEFAULT NULL COMMENT '资质id,鉴定随机绑定',
  `gender` smallint NULL DEFAULT NULL COMMENT '性别随机',
  `level` smallint NULL DEFAULT NULL COMMENT '等级,初始1级',
  `skillStr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '技能列表信息,有几率初始化出技能',
  `playerId` bigint NOT NULL COMMENT '玩家id',
  PRIMARY KEY (`uniqueId`) USING BTREE,
  INDEX `playerId`(`playerId`) USING BTREE COMMENT '玩家id作为索引查询'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player`  (
  `playerId` bigint NOT NULL COMMENT '角色ID',
  `accountName` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色账号,服务器生成的唯一账号,全服唯一',
  `inputName` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '玩家输入账号,记录下来用于排查玩家信息,玩家输入账号',
  `nickName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色昵称,游戏内角色自定义名称,全服唯一',
  `channel` int NOT NULL DEFAULT 0 COMMENT '渠道Id',
  `regTime` bigint NOT NULL COMMENT '注册时间',
  `lastTime` bigint NOT NULL COMMENT '最后登陆时间',
  `curServerId` int NOT NULL DEFAULT 0 COMMENT '当前服务器Id',
  `initServerId` int NOT NULL DEFAULT 0 COMMENT '玩家创角时的,最原始服务器Id',
  `level` smallint NOT NULL DEFAULT 1 COMMENT '等级',
  `exp` int NOT NULL DEFAULT 0 COMMENT '经验',
  `vip` smallint NOT NULL DEFAULT 0 COMMENT 'VIP等级',
  `vipExp` int NOT NULL DEFAULT 0 COMMENT 'VIP经验',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '角色状态,0:默认,1:禁言,2:封号,4:删号,8:其他',
  `propertieStr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '资源数据json格式',
  PRIMARY KEY (`playerId`) USING BTREE,
  UNIQUE INDEX `playerNickName`(`nickName`, `initServerId`) USING BTREE,
  INDEX `playerAccountName`(`accountName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for player_family
-- ----------------------------
DROP TABLE IF EXISTS `player_family`;
CREATE TABLE `player_family`  (
  `playerId` bigint NOT NULL COMMENT '玩家id',
  `joinTime` bigint NULL DEFAULT NULL COMMENT '加入时间',
  `leaveTime` bigint NULL DEFAULT NULL COMMENT '退出时间,强退记录,强制退出后间隔1天才能重新进入家族',
  `resetTime` bigint NULL DEFAULT NULL COMMENT '重置时间',
  `contribute` int NULL DEFAULT NULL COMMENT '个人家族贡献值',
  PRIMARY KEY (`playerId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for player_mail
-- ----------------------------
DROP TABLE IF EXISTS `player_mail`;
CREATE TABLE `player_mail`  (
  `id` bigint NOT NULL COMMENT '邮件ID',
  `playerId` bigint NOT NULL COMMENT '角色ID',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮件标题',
  `content` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮件内容',
  `rewards` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '奖励:{configId:num,configId:num}',
  `createTime` bigint NOT NULL COMMENT '邮件创建时间',
  `expireTime` bigint NOT NULL COMMENT '邮件过期时间',
  `state` tinyint NOT NULL DEFAULT 0 COMMENT '0=未读取;1=已读',
  PRIMARY KEY (`id`, `playerId`) USING BTREE,
  INDEX `playerId`(`playerId`) USING BTREE COMMENT '玩家id,作为索引查询'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '玩家个人邮件表' ROW_FORMAT = Dynamic;

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
  UNIQUE INDEX `rankServer`(`curServerId`, `rankType`, `uniqueId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统排行榜' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for recycle
-- ----------------------------
DROP TABLE IF EXISTS `recycle`;
CREATE TABLE `recycle`  (
  `playerId` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '玩家ID',
  `resourceId` bigint UNSIGNED NOT NULL COMMENT '资源唯一id',
  `configId` int NOT NULL COMMENT '道具配置id',
  `recieveTime` bigint NOT NULL COMMENT '获得的时间戳',
  `number` int NOT NULL COMMENT '当前数量',
  PRIMARY KEY (`resourceId`) USING BTREE,
  INDEX `playerItem`(`playerId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shadow
-- ----------------------------
DROP TABLE IF EXISTS `shadow`;
CREATE TABLE `shadow`  (
  `playerId` bigint NOT NULL COMMENT '玩家id',
  `data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '玩家数据',
  `updateTime` bigint NULL DEFAULT NULL COMMENT '更新时间,用于过滤数据',
  PRIMARY KEY (`playerId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`  (
  `playerId` bigint NOT NULL COMMENT '玩家id',
  `shopId` int NOT NULL COMMENT '商店类型id',
  `freeRefreshNum` smallint NULL DEFAULT NULL COMMENT '商店免费刷新次数',
  `freeRefreshTime` bigint NULL DEFAULT NULL COMMENT '商店免费刷新时间',
  `resRefreshNum` smallint NULL DEFAULT NULL COMMENT '商店资源刷新次数',
  `buyedStr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '已购买列表',
  `commoditiesStr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '当前商品列表,随机商品列表时使用',
  `resetTime` bigint NULL DEFAULT NULL COMMENT '最后重置时间,用于每日重置',
  PRIMARY KEY (`playerId`, `shopId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

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

SET FOREIGN_KEY_CHECKS = 1;
