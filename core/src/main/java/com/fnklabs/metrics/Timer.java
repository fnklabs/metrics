package com.fnklabs.metrics;


public interface Timer extends AutoCloseable {
    void stop();

    @Override
    default void close() {
        stop();
    }
}
