package com.cat.server.game.module.functioncontrol.time.parse;


public enum ResetTimePointParamType {
    /**
     * 每周第几天
     */
    DAYOFWEEK("dw", 0, 6),
    /**
     * 时<br>
     * 夏令时 时间为1:00~23:59<br>
     */
    HOUR("h", -1, 24),

    ;

    private final String key;

    private final int minValue;

    private final int maxValue;

    private ResetTimePointParamType(String key, int minValue, int maxValue) {
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

    public static ResetTimePointParamType selectParamType(String param) {
        ResetTimePointParamType[] values = ResetTimePointParamType.values();
        param = param.toLowerCase();
        for (ResetTimePointParamType type : values) {
            if (param.startsWith(type.key)) {
                return type;
            }
        }
        return null;
    }

}
