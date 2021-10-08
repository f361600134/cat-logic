package com.cat.server.game.module.activity.time.parse;

/**
 * 时间点类型表达式<br>
 * 需要支持一下种类型的时间点<br>
 * 1. 按照固定时间开启的时间点, 比如一些节假日譬如:春节, 元宵, 中秋, 端午, 国庆. 此种节日类型每个节日需要策划配置内容以及具体时间点, 开启活动<br>
 * 2. 按照开服时间开启的时间点, 如,根据开服时间x天后, 开启活动. 比如开服后3天后开启某个活动,7天后开启活动, 一周后开启, 一个月后开启等<br>
 * 3. 循环时间点, 此项适用于运营循环类的活动, 譬如, 某项活动A, 开服1个月后开启, 3个月后重复开启, 无限循环
 * 
 * @author Jeremy
 */
public enum TimePointParamType {
    /**
     * 年
     */
    YEAR("y"),
    /**
     * 月
     */
    MONTH("mo"),
    /**
     * 日
     */
    DAY("d"),
    /**
     * 时<br>
     * 夏令时 时间为1:00~24:59<br>
     * -1 每个小时 仅用于{@link HourTimePoint}
     */
    HOUR("h"),
    /**
     * 周<br>
     * w1为周1 w7为周日
     */
    WEEK("w"),
//    /**
//     * 分<br>
//     * -1 每分钟 仅用于{@link MinuteTimePoint}
//     */
//    MINUTE("mi"),
//    /**
//     * 秒
//     */
//    SECOND("s"),
    /**
     * 开服天数<br>
     * 开服当天为第1天,适用于以天作为间隔的时间
     */
    OPEN_SERVER_DAY("od"),
    /**
     * 开服周<br>
     * 开服当周为第0周,不论今天星期几,下一周作为第一周
     */
    OPEN_SERVER_WEEK("ow"),
    /**
     * 开服月<br>
     * 开服当周为第0月,不论今天几号,下一月作为第一个月
     */
    OPEN_SERVER_MONTH("om"),
    /**
     * 循环<br>
     * 必须配搭固定时间点/指定开服天数的时间使用
     */
    CYCLE("c"),
    /**
     * 循环次数，必须搭配循环使用
     */
    CYCLE_TIMES("ct"),
    /**
     * 持续时间(ms) 持续时间是毫秒为单位
     */
    DURATION("du"),
    ;

    private final String key;

    private TimePointParamType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static TimePointParamType selectParamType(String param) {
        TimePointParamType[] values = TimePointParamType.values();
        param = param.toLowerCase();
        for (TimePointParamType type : values) {
            if (param.endsWith(type.key)) {
                return type;
            }
        }
        return null;
    }

}
