package com.fnklabs.metrics.micrometer;

import com.fnklabs.metrics.Meter;

class MeterImpl implements Meter {
    private final io.micrometer.core.instrument.Counter counter;

    MeterImpl(io.micrometer.core.instrument.Counter counter) {
        this.counter = counter;
    }

    @Override
    public void inc() {
        counter.increment();
    }
}
