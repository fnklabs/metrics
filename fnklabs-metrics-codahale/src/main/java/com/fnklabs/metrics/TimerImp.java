package com.fnklabs.metrics;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

class TimerImp implements Timer {
    private final com.codahale.metrics.Timer.Context timer;
    private long deltaInNanoSeconds;

    TimerImp(com.codahale.metrics.Timer.Context timer) {
        this.timer = timer;
    }

    @Override
    public void stop() {
        deltaInNanoSeconds = timer.stop();
    }

    @Override
    public void close() throws IOException {
        timer.close();
    }

    @Override
    public String toString() {
        return String.format("%d ms", TimeUnit.NANOSECONDS.toMillis(deltaInNanoSeconds));
    }
}
