package com.fnklabs.metrics.dropwizard;

import com.fnklabs.metrics.Meter;

public class MeterImpl implements Meter {
    private final com.codahale.metrics.Meter meter;

    public MeterImpl(com.codahale.metrics.Meter meter) {this.meter = meter;}

    @Override
    public void inc() {
        meter.mark();
    }
}
