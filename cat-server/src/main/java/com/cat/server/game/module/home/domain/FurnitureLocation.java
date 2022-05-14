package com.cat.server.game.module.home.domain;

import com.cat.server.game.module.home.proto.PBHomeRectangleBuilder;

/**
 * 家具坐标定位信息
 * @author Jeremy
 */
public class FurnitureLocation {

    /**
     * x 轴
     */
    private int x;

    /**
     * y 轴
     */
    private int y;

    /**
     * 是否可以翻转
     */
    private boolean reverse;

    public FurnitureLocation() {
    }

    public FurnitureLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public FurnitureLocation(int x, int y, boolean reverse) {
        this.x = x;
        this.y = y;
        this.reverse = reverse;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }
    
    /**
     * 转int
     * @return
     */
    public int getReverse() {
        return reverse ? 1 : 0;
    }

    @Override
    public String toString() {
        return "FurnitureLocation{" +
                "x=" + x +
                ", y=" + y +
                ", reverse=" + reverse +
                '}';
    }
    
    /**
     * 创建一个方位对象
     */
    public static FurnitureLocation create(int x, int y, boolean reverse) {
    	return new FurnitureLocation(x, y, reverse);
    }
    
    /**
     * 转协议对象
     * @return
     */
    public PBHomeRectangleBuilder toProto() {
    	PBHomeRectangleBuilder builder = PBHomeRectangleBuilder.newInstance();
    	builder.setX(x);
    	builder.setY(y);
    	builder.setReverse(reverse);
    	return builder;
    }
    
}
