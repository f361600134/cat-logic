package com.cat.server.current;

import java.util.concurrent.CompletableFuture;

public class Demo1 {
    public static void main(String[] args) throws Exception {
    	System.out.println("===111===Thread's name:"+Thread.currentThread().getName());
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(Demo1::fetchPrice);
        // 如果执行成功:
        cf.thenAccept((result) -> {
            System.out.println("price: " + result);
            System.out.println("===333===Thread's name:"+Thread.currentThread().getName());
        });
        // 如果执行异常:
        cf.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });
        System.out.println("===444===Thread's name:"+Thread.currentThread().getName());
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static Double fetchPrice() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price failed!");
        }
        System.out.println("===222===Thread's name:"+Thread.currentThread().getName());
        return 5 + Math.random() * 20;
    }
}
