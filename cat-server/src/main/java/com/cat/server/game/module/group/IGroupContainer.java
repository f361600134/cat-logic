package com.cat.server.game.module.group;


import java.util.Collection;

/**
 * 团体容器接口类
 * @author Jeremy
 */
public interface IGroupContainer<T> {

    /**
     * 获得一个团体对象
     * @param groupId 团体id
     * @return 团体对象
     */
    public T get(long groupId);

    /**
     * 创建团体
     * @param memberId 成员id,创建人员默认为leader
     * @param groupName 团体名字
     * @date 2021年5月12日下午11:03:04
     * @return 团体对象
     */
    public T create(long memberId, String groupName);

    /**
     * 加入团体
     * @param member 成员对象
     * @param groupId 组id
     * @date 2021年5月12日下午10:50:55
     */
    public void enter(IMember member, long groupId);

    /**
     * 离开团体
     * @date 2021年5月12日下午10:50:55
     */
    public void exit(long memberId, long groupId);

    /**
     * 注销团体
     * @param t 表示团体对象
     * @date 2021年5月12日下午11:03:04
     */
    public void destroy(T t);

    /**
     * 校验名字是否重复
     * @param newName 名字
     * @return true 已存在改名字, false不存在
     */
    public boolean containsName(String newName);

    /**
     * 团体改名
     * @param groupId 团体iid
     * @param newName 团体名字
     * @return true表示改名成功
     */
    public boolean rename(long groupId, String newName);

    /**
     * 根据玩家id获取到团体id
     * @param playerId 玩家id
     * @return 团体id
     */
    public long getGroupIdByPlayerId(long playerId);

    /**
     * 根据玩家id获取到团体id
     * @param playerId 玩家id
     * @return 团体对象
     */
    public T getGroupByPlayerId(long playerId);

    /**
     * 按照关键字查询匹配的团体信息
     * @param keyword 关键字
     * @return 符合关键字的团体对象
     */
    public Collection<T> searchGroup(String keyword);
    
}
