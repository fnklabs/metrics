package com.fnklabs.metrics;

/**
 * Metrics interface
 */
public interface Metrics {

    /**
     * Retrieve started timer
     *
     * @param name Timer name
     *
     * @return Timer instance
     */
    Timer getTimer(String name);

    /**
     * Retrieve country
     *
     * @param counter Counter name
     *
     * @return Counter instance
     */
    Counter getCounter(String counter);

    /**
     * Dump all registered metrics
     */
    void report();
}
