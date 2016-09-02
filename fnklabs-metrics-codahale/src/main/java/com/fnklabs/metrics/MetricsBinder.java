package com.fnklabs.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;

import java.util.concurrent.TimeUnit;

public class MetricsBinder implements Metrics {
    /**
     * MetricsRegistry instance
     */
    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    /**
     * Reporter
     */
    private static final Slf4jReporter LOG_REPORTER = Slf4jReporter.forRegistry(METRIC_REGISTRY)
                                                                   .convertRatesTo(TimeUnit.SECONDS)
                                                                   .convertDurationsTo(TimeUnit.MILLISECONDS)
                                                                   .build();

    @Override
    public Timer getTimer(String name) {
        return new TimerImp(METRIC_REGISTRY.timer(name).time());
    }

    @Override
    public Counter getCounter(String counter) {
        return new CounterImp(METRIC_REGISTRY.counter(counter));
    }

    static {
        String reportPeriod = System.getProperty("fnklabs.metrics.report_period", "60");

        LOG_REPORTER.start(Long.parseLong(reportPeriod), TimeUnit.SECONDS);
    }
}
