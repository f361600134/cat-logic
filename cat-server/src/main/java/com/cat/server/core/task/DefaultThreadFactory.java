package com.cat.server.core.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义前缀的线程组<br>
 * 只是默认线程组自定义前缀<br>
 * {@link Executors#DefaultThreadFactory}
 * 
 * @author hdh
 *
 */
public class DefaultThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String namePrefix;

    private final ThreadGroup group;

    public DefaultThreadFactory(String namePrefix) {
        SecurityManager s = System.getSecurityManager();
        this.group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }

    public AtomicInteger getThreadNumber() {
        return threadNumber;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public ThreadGroup getGroup() {
        return group;
    }
}
