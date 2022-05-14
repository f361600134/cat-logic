package com.cat.server.game.module.home.utils;

/**
 * @author redback
 * @version 1.00
 * @time 2021-6-3 16:51
 */
public class HomeUtil {

    //通过唯一家具id 获取家具配置 id
    public static int getConfigIdByUniqueId(long uniqueId) {
        return (int) (uniqueId / 10000);
    }

    /**
     * 通过家具id，以及坐标信息，获取家具唯一 id
     * @param id 家具id
     * @param x  x坐标
     * @param y  y坐标
     * @param reverse 是否翻转1:是,0:否
     * @return 家具唯一id
     */
    public static long buildUniqueId(int id, int x, int y, int reverse) {
        return id * 10000L + x * 1000L + y * 100L + reverse;
    }

    /**
     * 构造家具坐标 id
     *
     * @param mapId  地图id
     * @param roomId 房间id
     * @param flatId 面 id
     * @return 家具坐标id
     */
    public static int buildLocationId(int mapId, int roomId, int flatId) {
        return mapId * 1000000 + roomId * 100 + flatId;
    }


    /**
     * 根据 locationId 获取面 id
     *
     * @param locationId 家具坐标id
     * @return 面 id
     */
    public static int getFlatIdByLocationId(int locationId) {
        return locationId % 100;
    }



    /**
     * 通过 locationId，获取家园(地图）id
     *
     * @param locationId 家具坐标id
     * @return 地图 id
     */
    public static int getMapIdByLocationId(int locationId) {
        return locationId / 1000000;
    }

    /**
     * 通过 locationId，获取房间 id
     *
     * @param locationId 家具坐标id
     * @return 房间 id
     */
    public static int getRoomIdByLocationId(int locationId) {
        return (locationId / 100) % 100;
    }


    /**
     * 通过房间 id，获取家园(地图）id
     *
     * @param roomId 房间id
     * @return 地图 id
     */
    public static int getHomeId(int roomId) {
        return roomId / 100;
    }


}
