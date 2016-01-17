package com.fnklabs.metrics;

class TimerImp implements Timer {
    private final com.codahale.metrics.Timer.Context timer;

    TimerImp(com.codahale.metrics.Timer.Context timer) {
        this.timer = timer;
    }

    @Override
    public void stop() {
        timer.stop();
    }
}
