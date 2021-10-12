package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;


/**
 * hd.活动时间表.xlsx<br>
 * activity_schedule_time.json<br>
 * 
 * @author auto gen
 *
 */
public class ConfigActivityScheduleTimeBase implements IGameConfig {

    /**
     * 活动 id
     */
    private int id;
    
    /**
     * 活动类型
     */
    private int type;
    
    /**
     * 活动方案<br>
     * 填0则为默认方案id，此功能尽量少用，如果需要使用，需要在对应的活动表中做好备注
     */
    private int planId;
    
    /**
     * 起始时间点<br>
     * 如果活动是循环活动，开始时间需要填循环时间和循环次数，如7天循环一次的活动，共循环3次，则需要在时间出加_7c_3ct。如果是无限循环的活动，则不需要填循环次数<br>
     * o.开服第几天<br>
     * w.每周几<br>
     * y.年<br>
     * mo.月<br>
     * d.日。如果单独使用，则是每个月第X天开启，如果按自然月循环，则不需要填后面的几个时间<br>
     * h.小时<br>
     * mi.分钟<br>
     * s.秒<br>
     * du.毫秒<br>
     * c.几天后开启循环<br>
     * ct.此循环会进行几次<br>
     * 例：<br>
     * 1.6w_10h表示每周6早上10点<br>
     * 2.2019y_12mo_4d_10h表示2019年12月4日10点0分0秒<br>
     * 3.2019y_12mo_4d_10h表示2019年12月4日10点<br>
     * 4.5o_12h_30mi表示开服第5天12点30分0秒<br>
     * 5.5o_12h_30mi_20c_3ct表示开服第5天12点30分0秒,开启活动后20天又开启一次本活动，共循环开启3次
     */
    private String startTime;
    
    /**
     * 准备时间<br>
     * 单位:秒<br>
     * 对应准备阶段<br>
     * 只是有预告 活动依然未开始<br>
     * 相关内容不可操作<br>
     * 起始时间点+准备时间=活动开始时间点<br>
     * 填-1表示不确定时间，需要读日历或者通过其他方式计算时间，一般只用于月循环的活动
     */
    private int prepareDuration;
    
    /**
     * 开始持续时间<br>
     * 单位:秒<br>
     * 对应活动进行阶段<br>
     * 活动开始时间点+开始持续时间=结算时间点<br>
     * 填-1表示不确定时间，需要读日历或者通过其他方式计算时间，一般只用于月循环的活动
     */
    private int beginDuration;
    
    /**
     * 结算持续时间<br>
     * 单位：秒<br>
     * 对应活动结算阶段<br>
     * 此阶段活动积分和排名停止变化，但玩家可以进行领奖，商店兑换等操作<br>
     * 结算时间点+结算持续时间=彻底结束时间点<br>
     * 填-1表示不确定时间，需要读日历或者通过其他方式计算时间，一般只用于月循环的活动
     */
    private int settleDuration;
    

    /**
     * get 活动 id
     *
     * @return
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * set 活动 id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get 活动类型
     *
     * @return
     */
    public int getType() {
        return this.type;
    }

    /**
     * set 活动类型
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * get 活动方案
     *
     * @return
     */
    public int getPlanId() {
        return this.planId;
    }

    /**
     * set 活动方案
     *
     * @param planId
     */
    public void setPlanId(int planId) {
        this.planId = planId;
    }

    /**
     * get 起始时间点
     *
     * @return
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * set 起始时间点
     *
     * @param startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * get 准备时间
     *
     * @return
     */
    public int getPrepareDuration() {
        return this.prepareDuration;
    }

    /**
     * set 准备时间
     *
     * @param prepareDuration
     */
    public void setPrepareDuration(int prepareDuration) {
        this.prepareDuration = prepareDuration;
    }

    /**
     * get 开始持续时间
     *
     * @return
     */
    public int getBeginDuration() {
        return this.beginDuration;
    }

    /**
     * set 开始持续时间
     *
     * @param beginDuration
     */
    public void setBeginDuration(int beginDuration) {
        this.beginDuration = beginDuration;
    }

    /**
     * get 结算持续时间
     *
     * @return
     */
    public int getSettleDuration() {
        return this.settleDuration;
    }

    /**
     * set 结算持续时间
     *
     * @param settleDuration
     */
    public void setSettleDuration(int settleDuration) {
        this.settleDuration = settleDuration;
    }

}
