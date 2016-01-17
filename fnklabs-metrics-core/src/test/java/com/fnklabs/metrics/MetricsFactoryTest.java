package com.fnklabs.metrics;


import org.junit.Assert;
import org.junit.Test;

public class MetricsFactoryTest {

    @Test(expected = Throwable.class)
    public void testGetMetrics() throws Exception {
        Metrics metrics = MetricsFactory.getMetrics();

        Assert.fail();
    }
}