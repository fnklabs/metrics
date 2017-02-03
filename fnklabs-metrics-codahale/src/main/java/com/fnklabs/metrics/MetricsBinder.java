package com.fnklabs.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;

public class MetricsBinder implements Metrics {

    /**
     * MetricsRegistry instance
     */
    private final MetricRegistry metricRegistry = new MetricRegistry();

    /**
     * Reporter
     */
    private final Slf4jReporter logReporter;

    public MetricsBinder(Configuration configuration) {
        logReporter = Slf4jReporter.forRegistry(metricRegistry)
                                   .convertRatesTo(configuration.getRatesUnit())
                                   .convertDurationsTo(configuration.getDurationUnit())
                                   .build();

        logReporter.start(configuration.getReportPeriod(), configuration.getReportPeriodUnit());
    }


    @Override
    public Timer getTimer(String name) {
        return new TimerImpl(metricRegistry.timer(name).time());
    }

    @Override
    public Counter getCounter(String counter) {
        return new CounterImpl(metricRegistry.counter(counter));
    }

    @Override
    public void report() {
        logReporter.report();
    }
}
