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

    /**
     * Retrieve started timer with tags. Implementations that don't support tags ignore them.
     *
     * @param name Timer name
     * @param tags Timer tags
     *
     * @return Timer instance
     */
    default Timer getTimer(String name, Tag... tags) {
        return getTimer(name);
    }

    /**
     * Retrieve counter with tags. Implementations that don't support tags ignore them.
     *
     * @param counter Counter name
     * @param tags    Counter tags
     *
     * @return Counter instance
     */
    default Counter getCounter(String counter, Tag... tags) {
        return getCounter(counter);
    }

    /**
     * Retrieve meter with tags. Implementations that don't support tags ignore them.
     *
     * @param name Meter name
     * @param tags Meter tags
     *
     * @return Meter instance
     */
    default Meter getMeter(String name, Tag... tags) {
        return getMeter(name);
    }

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
     * Register Gauge with tags. Implementations that don't support tags ignore them.
     *
     * @param name  Gauge name
     * @param gauge Gauge instance
     * @param tags  Gauge tags
     * @param <T>   Gauge return type
     */
    default <T extends Number> void registerGauge(String name, Gauge<T> gauge, Tag... tags) {
        registerGauge(name, gauge);
    }

    /**
     * Dump all registered metrics
     */
    void report();
}
