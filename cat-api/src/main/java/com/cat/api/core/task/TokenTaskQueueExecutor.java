package com.cat.api.core.task;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 带标记的任务队列处理类<br>
 * 标记相同的任务 将分配到相同线程中处理<br>
 * 只保证标记相同的任务队列是单线程 有序的处理<br>
 * 不保证同标记的任务每次都在相同线程处理<br>
 * 也不保证不同标记的任务在处理顺序有序
 * 
 * 
 * @author hdh
 *
 */
public interface TokenTaskQueueExecutor {

    /**
     * 提交任务
     * 
     * @param token 标记 若标记相同 则任务将在同1线程中处理
     * @param task
     */
    void submit(long token, Task task);

    /**
     * 提交任务<br>
     * 若指定的标记对应的任务队列 是当前正在执行的线程<br>
     * 则不再添加到队列末端 而是直接执行该任务<br>
     * 避免队列前面的任务阻塞等待后续的任务的完成<br>
     * 若无必要 避免使用该方法
     * 
     * @param <V>
     * @param token    标记 若标记相同 则任务将在同1线程中处理
     * @param callable 带有回调的任务
     * @return
     */
    <V> Future<V> submit(long token, Callable<V> callable);
}
