package com.fnklabs.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.jmx.JmxReporter;

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

        if (configuration.isJmxEnable()) {
            JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry)
                                                 .convertRatesTo(configuration.getRateUnit())
                                                 .convertDurationsTo(configuration.getDurationUnit())
                                                 .build();

            jmxReporter.start();
        }
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
    public Meter getMeter(String name) {
        return new MeterImpl(metricRegistry.meter(name));
    }

    @Override
    public <T extends Number> void registerGauge(String name, Gauge<T> gauge) {
        metricRegistry.register(name, (com.codahale.metrics.Gauge<T>) gauge::getValue);
    }

    @Override
    public void report() {
        logReporter.report();
    }
}
