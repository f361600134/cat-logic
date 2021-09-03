package com.cat.server.current;

import java.util.concurrent.CompletableFuture;

public class Demo2 {
    public static void main(String[] args) throws Exception {
    	System.out.println("===111===Thread's name:"+Thread.currentThread().getName());
        // 第一个任务:
        CompletableFuture<String> cfQuery = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油");
        });
        // cfQuery成功后继续执行下一个任务:
        CompletableFuture<Double> cfFetch = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice(code);
        });
        // cfFetch成功后打印结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        System.out.println("===444===Thread's name:"+Thread.currentThread().getName());
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(2000);
    }

    static String queryCode(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("===222===Thread's name:"+Thread.currentThread().getName());
        return "601857";
    }

    static Double fetchPrice(String code) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("===333===Thread's name:"+Thread.currentThread().getName());
        return 5 + Math.random() * 20;
    }
}