package com.cat.server.core.task;

import java.util.concurrent.ScheduledFuture;

public class DefaultTaskHandle implements TaskHandle {

    private final ScheduledFuture<?> future;

    public DefaultTaskHandle(ScheduledFuture<?> future) {
        this.future = future;
    }

    @Override
    public void cancel() {
        if (future != null) {
            future.cancel(false);
        }
    }

    public ScheduledFuture<?> getFuture() {
        return future;
    }

}
