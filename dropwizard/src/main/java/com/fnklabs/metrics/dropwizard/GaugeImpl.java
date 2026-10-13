package com.fnklabs.metrics.dropwizard;

import com.fnklabs.metrics.Gauge;

public class GaugeImpl<T extends Number> implements Gauge<T> {
    private final com.codahale.metrics.Gauge<T> gauge;

    public GaugeImpl(com.codahale.metrics.Gauge<T> gauge) {this.gauge = gauge;}

    @Override
    public T getValue() {
        return gauge.getValue();
    }
}
