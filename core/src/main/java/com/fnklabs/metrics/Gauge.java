package com.fnklabs.metrics;

/**
 * A gauge is an instantaneous measurement of a value.
 *
 * @param <T>
 */
public interface Gauge<T extends Number> {
    /**
     * Returns the metric's current value.
     */
    T getValue();
}
