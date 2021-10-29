package com.cat.api.core.task;

import java.util.concurrent.Callable;

import com.google.common.util.concurrent.FutureCallback;

/**
 * 带有监听回调的定时器接口
 * @author Jeremy
 */
public interface ListenableExecutor {
	
    /**
     * 提交一个带有回调方法的任务<br>
     * @param <V>
     * @param callable 带有回调的任务
     * @param callback 如果有回调, 则在本线程内直接调用执行
     * @return
     */
    <V> void submit(Callable<V> callable, FutureCallback<V> callback);
	
}
