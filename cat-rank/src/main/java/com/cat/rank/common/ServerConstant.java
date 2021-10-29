package com.cat.rank.common;

/**
 * 系统常量, 不配置的数据
 *
 * @author Jeremy
 * @date 2020年8月25日上午10:41:05
 */
public class ServerConstant {

    /**
     * json的目录配置
     */
    public static String JsonPath = "src/main/resources/configdata/";

    /**
     * 扫描的目录
     */
    public static String scanPath = "com.cat.rank";

    /**
     * 游戏节点分组-游戏逻辑节点
     */
    public static String NODE_TYPE_GAME = "game";
    /**
     * 游戏节点分组-登录节点
     */
    public static String NODE_TYPE_LOGIN = "login";
    /**
     * 游戏节点分组-战斗服节点
     */
    public static String NODE_TYPE_BATTLE = "battle";
    /**
     * 游戏节点分组-排行榜节点
     */
    public static String NODE_TYPE_RANK = "rank";

}
