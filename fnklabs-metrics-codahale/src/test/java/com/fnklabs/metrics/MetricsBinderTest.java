package com.fnklabs.metrics;

import org.junit.Assert;
import org.junit.Test;

public class MetricsBinderTest {

    @Test
    public void testGetTimer() throws Exception {
        Metrics metrics = MetricsFactory.getMetrics();
        Timer testTimer = metrics.getTimer("test_timer");

        Assert.assertNotNull(testTimer);
    }

    @Test
    public void testGetCounter() throws Exception {
        Metrics metrics = MetricsFactory.getMetrics();

        Counter testCounter = metrics.getCounter("test_counter");

        Assert.assertNotNull(testCounter);
    }
}