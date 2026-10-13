package com.fnklabs.metrics;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MetricsFactoryTest {

    @Test
    public void testGetMetrics() throws Exception {
        Assertions.assertThrows(Throwable.class, MetricsFactory::getMetrics);
    }
}
