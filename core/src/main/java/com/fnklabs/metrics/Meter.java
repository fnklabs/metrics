package com.fnklabs.metrics;

/**
 * A meter measures the rate of events over time (e.g., “requests per second”). In addition to the mean rate, meters also track 1-, 5-, and 15-minute moving averages.
 */
public interface Meter {
    /**
     * Increment the occurrence of an event.
     */
    void inc();
}
