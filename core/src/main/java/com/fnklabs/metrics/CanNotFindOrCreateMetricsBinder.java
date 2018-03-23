package com.fnklabs.metrics;

public class CanNotFindOrCreateMetricsBinder extends RuntimeException {
    public CanNotFindOrCreateMetricsBinder(ReflectiveOperationException e) {
        super(e);
    }
}
