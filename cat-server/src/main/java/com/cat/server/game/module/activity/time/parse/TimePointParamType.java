package com.cat.server.game.module.activity.time.parse;


public enum TimePointParamType {
    /**
     * 年
     */
    YEAR("y", -1, -1),
    /**
     * 月
     */
    MONTH("mo", 1, 12),
    /**
     * 日
     */
    DAY("d", 1, 31),
    /**
     * 时<br>
     * 夏令时 时间为1:00~23:59<br>
     */
    HOUR("h", -1, 24),
//    /**
//     * 分<br>
//     * -1 每分钟 仅用于{@link MinuteTimePoint}
//     */
//    MINUTE("mi", -1, 59),
//    /**
//     * 秒
//     */
//    SECOND("s", 0, 59),
    /**
     * 周<br>
     * w1为周1 w7为周日
     */
    WEEK("w", 1, 7),
    /**
     * 开服天数<br>
     * 开服当天为第1天
     */
    OPEN_SERVER_DAY("o", 1, -1),
    /**
     * 循环<br>
     * 必须配搭固定时间点/指定开服天数的时间使用
     */
    CYCLE("c", 1, -1),


    /**
     * 循环次数，必须搭配循环使用
     */
    CYCLE_TIMES("ct", 1, -1),

    /**
     * 持续时间(ms) 持续时间是毫秒为单位
     */
    DURATION("du", 1, -1),

    ;

    private final String key;

    private final int minValue;

    private final int maxValue;

    private TimePointParamType(String key, int minValue, int maxValue) {
        this.key = key;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public String getKey() {
        return key;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    /**
     * 判断该值是否有效
     * 
     * @param value
     * @return
     */
    public boolean checkValid(int value) {
        if (minValue != -1 && value < minValue) {
            return false;
        }
        if (maxValue != -1 && value > maxValue) {
            return false;
        }
        return true;
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
