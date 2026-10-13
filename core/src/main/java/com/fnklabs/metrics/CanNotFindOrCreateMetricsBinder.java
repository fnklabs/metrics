package com.fnklabs.metrics;

public class CanNotFindOrCreateMetricsBinder extends RuntimeException {
    public CanNotFindOrCreateMetricsBinder(String message) {
        super(message);
    }
}
