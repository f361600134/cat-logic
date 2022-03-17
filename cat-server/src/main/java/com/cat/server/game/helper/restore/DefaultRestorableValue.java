package com.cat.server.game.helper.restore;

public class DefaultRestorableValue implements RestorableValue {
    /**
     * 当前数量
     */
    protected int num;
    /**
     * 上次操作/恢复时间
     */
    protected long lastTime;

    public DefaultRestorableValue() {
    }

    public DefaultRestorableValue(int num, long lastTime) {
        this.num = num;
        this.lastTime = lastTime;
    }

    @Override
    public int getNum() {
        return num;
    }

    @Override
    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public long getLastTime() {
        return lastTime;
    }

    @Override
    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return "DefaultRestorableValue [num=" + num + ", lastTime=" + lastTime + "]";
    }
}
