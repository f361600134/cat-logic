package com.cat.server.core.config.container;

import java.util.Map;

/**
 * 游戏配置容器<br>
 * 保存一种配置的容器
 *  @aothor Jeremy
 */
public interface IConfigContainer<T extends IGameConfig> {
    /**
     * 对应的协议类
     * 
     * @return
     */
    Class<T> getConfigClazz();

    /**
     * 对应的配置文件名
     * 
     * @return
     */
    String getFileName();

    /**
     * 加载相关的所有配置
     * 
     * @throws Exception
     */
    void loadAll() throws Exception;

    /**
     * 根据文本内容 加载配置<br>
     * 该方法会清空原配置内容
     * 
     * @param content
     * @throws Exception
     */
    void load(String content) throws Exception;

    /**
     * 加载完成后执行<br>
     * 启动时为所有配置loadAll后执行<br>
     * 热更配置时 为配置load后执行
     * 
     * @param startup 是否启动时调用
     */
    void afterLoad(boolean startup);

    /**
     * 获取所有配置
     * 
     * @return
     */
    Map<Integer, T> getAllConfigs();

    /**
     * 根据配置id获取配置
     * 
     * @param id
     * @return
     */
    T getConfig(int id);

    /**
     * 获取唯一的配置<br>
     * 若该配置类对应的配置只有1条 且id为1<br>
     * 可使用该方式获取<br>
     * 若有多条 返回错误
     * 
     * @return
     */
    T getUnique();
    
    /**
     * 刷新配置<br>
     */
    void refresh();

}
