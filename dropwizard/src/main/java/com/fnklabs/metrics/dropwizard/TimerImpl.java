package com.fnklabs.metrics.dropwizard;

import com.fnklabs.metrics.Timer;

class TimerImpl implements Timer {
    private final com.codahale.metrics.Timer.Context timer;

    TimerImpl(com.codahale.metrics.Timer.Context timer) {
        this.timer = timer;
    }

    @Override
    public void stop() {
        timer.stop();
    }
}
