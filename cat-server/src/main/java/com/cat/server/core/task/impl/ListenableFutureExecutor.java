package com.cat.server.core.task.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.annotation.NotUse;
import com.cat.server.core.task.DefaultFutureTask;
import com.cat.server.core.task.DefaultThreadFactory;
import com.cat.server.core.task.ListenableExecutor;
import com.cat.server.core.task.Task;
import com.cat.server.core.task.TokenTaskQueue;
import com.cat.server.core.task.TokenTaskQueueExecutor;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * 
 * 想实现一个基于Actor的异步框架, 且可以支持回调
 * 但是目前来说, 这套做法有点问题. 不能共享模块的数据, 在同一条线程下执行
 * @author Jeremy 
 */
@NotUse
public class ListenableFutureExecutor implements TokenTaskQueueExecutor, ListenableExecutor {

    protected final static int DEFAULT_QUEUE_MAX_SIZE = 16;

    protected final static int CACHE_EXPIRED = 30;

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 标记,任务队列
     */
    protected final Cache<Long, TokenTaskQueue> tokenQueues;

    protected final ListeningExecutorService executor;

    /**
     * 构造器, 使用默认处理器核心数作为cpu数量
     * @param threadPrefix 线程前缀
     */
    public ListenableFutureExecutor(String threadPrefix) {
        ThreadFactory threadFactory = new DefaultThreadFactory(threadPrefix);
        int cpuNum = Runtime.getRuntime().availableProcessors();
        this.executor = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(cpuNum + 1, threadFactory));
        this.tokenQueues = newQueueCache();
    }

    /**
     * 构造器, 指定前缀, 线程池大小
     * @param threadPrefix 线程前缀
     * @param poolSize 线程池大小
     */
    public ListenableFutureExecutor(String threadPrefix, int poolSize) {
        ThreadFactory threadFactory = new DefaultThreadFactory(threadPrefix);
        this.executor = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(poolSize, threadFactory));
        this.tokenQueues = newQueueCache();
    }
    
    protected Cache<Long, TokenTaskQueue> newQueueCache() {
        return CacheBuilder.newBuilder()//
                .expireAfterAccess(CACHE_EXPIRED, TimeUnit.MINUTES) // 指定时间内未读写过时移除
                .build();
    }

    @Override
    public void submit(long token, Task task) {
        TokenTaskQueue tokenQueue = getQueue(token);
        tokenQueue.addTask(task);
        if (!tokenQueue.isRunning()) {
            executor.submit(tokenQueue);
        }
    }

    @Override
    public <V> Future<V> submit(long token, Callable<V> callable) {
        DefaultFutureTask<V> task = new DefaultFutureTask<>(callable);
        TokenTaskQueue tokenQueue = getQueue(token);
        // 判断是否当前线程正在执行该任务队列
        Thread runThread = tokenQueue.getRunThread();
        Thread curThread = Thread.currentThread();
        if (curThread.equals(runThread)) {
            // 当前线程正在执行该任务队列
            // 直接执行该任务 不再扔进任务队列中
            // 避免队列前面的任务阻塞等待队列后面的任务完成
            task.execute();
        } else {
            tokenQueue.addTask(task);
            if (!tokenQueue.isRunning()) {
                executor.submit(tokenQueue);
            }
        }
        return task;
    }

    protected TokenTaskQueue getQueue(long token) {
        try {
            TokenTaskQueue queue = tokenQueues.get(token, () -> {
                return newQueue(token);
            });
            return queue;
        } catch (ExecutionException e) {
            logger.error("getQueue[" + token + "] error.", e);
        }
        return newQueue(token);
    }

    protected TokenTaskQueue newQueue(long token) {
        // 队列有默认大小 超出无法处理时 抛弃新任务
        return new TokenTaskQueue(token, new LinkedBlockingQueue<>(DEFAULT_QUEUE_MAX_SIZE));
    }

	@Override
	public <V> void submit(Callable<V> callable, FutureCallback<V> callback) {
		ListenableFuture<V> future = executor.submit(callable);
		// 添加回调
		Futures.addCallback(future, callback, executor);
	}


}
