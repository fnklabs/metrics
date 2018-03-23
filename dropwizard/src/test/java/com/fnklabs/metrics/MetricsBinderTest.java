package com.fnklabs.metrics;

import org.junit.*;

public class MetricsBinderTest {
    private Metrics metrics;

    @Before
    public void setUp() throws Exception {
        metrics = MetricsFactory.getMetrics();
    }

    @After
    public void tearDown() throws Exception {
        metrics.report();
    }

    @Test
    public void testGetTimer() throws Exception {
        Timer testTimer = metrics.getTimer("test_timer");

        Assert.assertNotNull(testTimer);
    }

    @Test
    public void testGetCounter() throws Exception {
        Counter testCounter = metrics.getCounter("test_counter");

        Assert.assertNotNull(testCounter);
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
    public void testCreateName() {
        String name = metrics.createName(MetricsBinderTest.class, "test", "name");

        Assert.assertEquals("com.fnklabs.metrics.MetricsBinderTest.test.name", name);
    }

    @Ignore("manual test")
    @Test
    public void jmx() throws Exception {
        testGetTimer();
        testGetCounter();
        testGauge();
        testMeter();

        Thread.sleep(360_000);
    }
}