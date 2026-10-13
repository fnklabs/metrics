package com.fnklabs.metrics.micrometer;

import com.fnklabs.metrics.Timer;

class TimerImpl implements Timer {
    private final io.micrometer.core.instrument.Timer.Sample sample;
    private final io.micrometer.core.instrument.Timer timer;

    TimerImpl(io.micrometer.core.instrument.Timer.Sample sample, io.micrometer.core.instrument.Timer timer) {
        this.sample = sample;
        this.timer = timer;
    }

    @Override
    public void stop() {
        sample.stop(timer);
    }
}
