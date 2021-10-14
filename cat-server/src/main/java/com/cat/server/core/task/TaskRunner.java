package com.cat.server.core.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskRunner implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(TaskRunner.class);

    private final Task task;

    public TaskRunner(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        try {
            task.execute();
        } catch (Exception e) {
            // 定时任务若在执行期间抛错会导致后续任务停止执行
            logger.error("task[" + task.getClass().getSimpleName() + "] execute error.", e);
        }
    }

    public Task getTask() {
        return task;
    }

}
