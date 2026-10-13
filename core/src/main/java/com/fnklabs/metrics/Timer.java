package com.fnklabs.metrics;


/**
 * Represents a mechanism to record the duration of a specific event or operation.
 * The timer is typically started when the operation begins and is explicitly stopped
 * at the end of the operation to record its duration.
 *
 * Implementations may integrate with different metric libraries to track and measure
 * performance statistics.
 *
 * This interface extends {@link AutoCloseable}, allowing it to be used in try-with-resources
 * blocks, where {@link #close()} automatically delegates to {@link #stop()}.
 */
public interface Timer extends AutoCloseable {
    /**
     * Stops the timer and records the elapsed duration since it was started.
     *
     * This method is intended to be called explicitly to mark the completion
     * of a timed operation. Once called, the recorded duration can be used
     * for performance analysis, metrics reporting, or other tracking purposes.
     *
     * If the timer is part of a resource management block (e.g., try-with-resources),
     * this method is automatically invoked by the {@link #close()} method.
     */
    void stop();

    @Override
    default void close() {
        stop();
    }
}
