package com.cat.api.core.task;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 带标记的队列 <br>
 *
 *
 * @author hdh
 *
 */
public class TokenTaskQueue implements TaskQueue {

    private final static Logger logger = LoggerFactory.getLogger(TokenTaskQueue.class);

    private final long token;

    private final Queue<Task> tasks;

    private volatile AtomicBoolean running = new AtomicBoolean();
    /**
     * 当前的执行线程
     */
    private volatile Thread runThread;

    public TokenTaskQueue(long token, Queue<Task> tasks) {
        super();
        this.token = token;
        this.tasks = tasks;
    }

    public Queue<Task> getTasks() {
        return tasks;
    }

    public long getToken() {
        return token;
    }

    @Override
    public void run() {
        if (!running.compareAndSet(false, true)) {
            return;
        }
        try {
            runThread = Thread.currentThread();
            executeTasks();
        } finally {
            runThread = null;
            running.set(false);
        }
        if (!tasks.isEmpty()) {
            // 执行完后 又添加了新任务
            run();
        }
    }

    private void executeTasks() {
        while (true) {
            Task task = tasks.poll();
            if (task == null) {
                break;
            }
            try {
                task.execute();
            } catch (Exception e) {
                logger.error("token[{}] execute task[{}] error.", token, task.toDesc(), e);
            }
        }

    }

    @Override
    public void addTask(Task task) {
        boolean flag = this.tasks.offer(task);
        if (!flag) {
            logger.error("token[{}] add task[{}] error.runThread[{}]", token, task.toDesc(), getRunThreadName());
        }
    }

    /**
     * 获取当前在执行任务的线程名
     * 
     * @return
     */
    private String getRunThreadName() {
        Thread tmpThread = runThread;
        if (tmpThread == null) {
            return null;
        }
        return tmpThread.getName();
    }

    public AtomicBoolean getRunning() {
        return running;
    }

    public void setRunning(AtomicBoolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running.get();
    }

    @Override
    public Thread getRunThread() {
        return runThread;
    }

    public void setRunThread(Thread runThread) {
        this.runThread = runThread;
    }

}
