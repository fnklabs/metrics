package com.fnklabs.metrics;


import java.io.Closeable;

public interface Timer extends Closeable {
    void stop();
}
