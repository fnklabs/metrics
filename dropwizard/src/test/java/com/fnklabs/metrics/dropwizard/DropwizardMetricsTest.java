package com.fnklabs.metrics.dropwizard;

import com.fnklabs.metrics.Counter;
import com.fnklabs.metrics.Gauge;
import com.fnklabs.metrics.Meter;
import com.fnklabs.metrics.Metrics;
import com.fnklabs.metrics.MetricsFactory;
import com.fnklabs.metrics.Tag;
import com.fnklabs.metrics.Timer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class DropwizardMetricsTest {
    private Metrics metrics;

    @BeforeEach
    public void setUp() throws Exception {
        metrics = MetricsFactory.getMetrics();
    }

    @AfterEach
    public void tearDown() throws Exception {
        metrics.report();
    }

    @Test
    public void testServiceLoaderBinding() {
        Assertions.assertTrue(metrics instanceof DropwizardMetrics);
    }

    @Test
    public void testGetTimer() throws Exception {
        Timer testTimer = metrics.getTimer("test_timer");

        Assertions.assertNotNull(testTimer);
    }

    @Test
    public void testGetCounter() throws Exception {
        Counter testCounter = metrics.getCounter("test_counter");

        Assertions.assertNotNull(testCounter);
    }

    @Test
    public void testMeter() {
        Meter meter = metrics.getMeter("test_meter");

        meter.inc();
        meter.inc();
    }

    @Test
    public void testGauge() throws InterruptedException {
        metrics.registerGauge("test_gauge", new Gauge<Number>() {
            @Override
            public Number getValue() {
                return System.currentTimeMillis();
            }
        });
    }

    @Test
    public void testTagsAreIgnored() {
        metrics.getCounter("test_tagged_counter", Tag.of("queue", "a")).inc();
        metrics.getCounter("test_tagged_counter", Tag.of("queue", "b")).inc();

        Assertions.assertEquals("2", metrics.getCounter("test_tagged_counter").toString());

        try (Timer timer = metrics.getTimer("test_tagged_timer", Tag.of("method", "GET"))) {
            Assertions.assertNotNull(timer);
        }

        metrics.getMeter("test_tagged_meter", Tag.of("status", "ok")).inc();
        metrics.registerGauge("test_tagged_gauge", () -> 7, Tag.of("pool", "main"));
    }

    @Test
    public void testCreateName() {
        String name = metrics.createName(DropwizardMetricsTest.class, "test", "name");

        Assertions.assertEquals("com.fnklabs.metrics.dropwizard.DropwizardMetricsTest.test.name", name);
    }

    @Disabled("manual test")
    @Test
    public void jmx() throws Exception {
        testGetTimer();
        testGetCounter();
        testGauge();
        testMeter();

        Thread.sleep(360_000);
    }
}