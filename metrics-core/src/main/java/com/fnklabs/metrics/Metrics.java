package com.fnklabs.metrics;

public interface Metrics {
    Timer getTimer(String name);

    Counter getCounter(String counter);
}
