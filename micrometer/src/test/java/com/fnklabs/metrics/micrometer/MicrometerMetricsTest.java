package com.fnklabs.metrics.micrometer;

import com.fnklabs.metrics.Counter;
import com.fnklabs.metrics.Meter;
import com.fnklabs.metrics.Metrics;
import com.fnklabs.metrics.MetricsFactory;
import com.fnklabs.metrics.Tag;
import com.fnklabs.metrics.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MicrometerMetricsTest {
    /**
     * Cumulative registry attached to the global registry like an application backend (e.g. Prometheus). Values can't
     * be read from the global composite registry itself: it reads the first child registry, which may be the step based
     * logging registry that reports 0 until the report period ends.
     */
    private static final SimpleMeterRegistry registry = new SimpleMeterRegistry();

    private Metrics metrics;

    @BeforeAll
    public static void addRegistry() {
        io.micrometer.core.instrument.Metrics.addRegistry(registry);
    }

    @AfterAll
    public static void removeRegistry() {
        io.micrometer.core.instrument.Metrics.removeRegistry(registry);
    }

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
        Assertions.assertTrue(metrics instanceof MicrometerMetrics);
    }

    @Test
    public void testGetTimer() throws Exception {
        try (Timer testTimer = metrics.getTimer("test_timer")) {
            Assertions.assertNotNull(testTimer);
        }

        Assertions.assertTrue(registry.get("test_timer").timer().count() > 0);
    }

    @Test
    public void testGetCounter() throws Exception {
        Counter testCounter = metrics.getCounter("test_counter");

        testCounter.inc(5);
        testCounter.dec();
        metrics.getCounter("test_counter").dec(2);

        Assertions.assertEquals(2, registry.get("test_counter").gauge().value(), 0);
    }

    @Test
    public void testMeter() {
        Meter meter = metrics.getMeter("test_meter");

        meter.inc();
        meter.inc();

        Assertions.assertEquals(2, registry.get("test_meter").counter().count(), 0);
    }

    @Test
    public void testGauge() throws InterruptedException {
        metrics.registerGauge("test_gauge", () -> 42);

        Assertions.assertEquals(42, registry.get("test_gauge").gauge().value(), 0);
    }

    @Test
    public void testTags() {
        try (Timer timer = metrics.getTimer("test_tagged_timer", Tag.of("method", "GET"))) {
            Assertions.assertNotNull(timer);
        }

        metrics.getCounter("test_tagged_counter", Tag.of("queue", "a")).inc(3);
        metrics.getCounter("test_tagged_counter", Tag.of("queue", "b")).inc();
        metrics.getCounter("test_tagged_counter", Tag.of("queue", "a")).dec();

        metrics.getMeter("test_tagged_meter", Tag.of("status", "ok")).inc();
        metrics.getMeter("test_tagged_meter", Tag.of("status", "error")).inc();
        metrics.getMeter("test_tagged_meter", Tag.of("status", "error")).inc();

        metrics.registerGauge("test_tagged_gauge", () -> 7, Tag.of("pool", "main"));

        Assertions.assertEquals(1, registry.get("test_tagged_timer").tag("method", "GET").timer().count());
        Assertions.assertEquals(2, registry.get("test_tagged_counter").tag("queue", "a").gauge().value(), 0);
        Assertions.assertEquals(1, registry.get("test_tagged_counter").tag("queue", "b").gauge().value(), 0);
        Assertions.assertEquals(1, registry.get("test_tagged_meter").tag("status", "ok").counter().count(), 0);
        Assertions.assertEquals(2, registry.get("test_tagged_meter").tag("status", "error").counter().count(), 0);
        Assertions.assertEquals(7, registry.get("test_tagged_gauge").tag("pool", "main").gauge().value(), 0);
    }

    @Test
    public void testCreateName() {
        String name = metrics.createName(MicrometerMetricsTest.class, "test", "name");

        Assertions.assertEquals("com.fnklabs.metrics.micrometer.MicrometerMetricsTest.test.name", name);
    }
}
