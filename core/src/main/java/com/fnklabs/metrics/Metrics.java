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
     * A meter measures the rate of events over time (e.g., “requests per second”). In addition to the mean rate, meters also track 1-, 5-, and 15-minute moving averages.
     *
     * @param name Meter name
     *
     * @return Meter instance
     */
    Meter getMeter(String name);

    default String createName(Class clazz, String... params) {
        StringBuilder builder = new StringBuilder();

        builder.append(clazz.getName());

        for (String param : params) {
            builder.append(".").append(param);
        }

        return builder.toString();
    }

    /**
     * Register Gauge
     *
     * @param name  Gauge name
     * @param gauge Gauge instance
     * @param <T>   Gauge return type
     */
    <T extends Number> void registerGauge(String name, Gauge<T> gauge);

    /**
     * Dump all registered metrics
     */
    void report();
}
