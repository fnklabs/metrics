package com.fnklabs.metrics.micrometer;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import com.fnklabs.metrics.Configuration;
import com.fnklabs.metrics.Counter;
import com.fnklabs.metrics.Gauge;
import com.fnklabs.metrics.Meter;
import com.fnklabs.metrics.Metrics;
import com.fnklabs.metrics.Tag;
import com.fnklabs.metrics.Timer;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Micrometer based metrics.
 * <p>
 * By default all meters are registered in {@link io.micrometer.core.instrument.Metrics#globalRegistry}, so they are
 * also exported by any registry the application adds to it (Prometheus, OTLP, etc.).
 */
public class MicrometerMetrics implements Metrics {
    private static final Tag[] NO_TAGS = new Tag[0];

    /**
     * Registry that holds all meters
     */
    private final CompositeMeterRegistry registry;

    /**
     * Reporter
     */
    private final ReportingLoggingMeterRegistry logRegistry;

    /**
     * Micrometer counters are monotonic, so up/down counters are backed by gauges; map keeps strong references to
     * gauge values
     */
    private final ConcurrentMap<CounterKey, AtomicLong> counters = new ConcurrentHashMap<>();

    public MicrometerMetrics(Configuration configuration) {
        this(io.micrometer.core.instrument.Metrics.globalRegistry, configuration);
    }

    public MicrometerMetrics(CompositeMeterRegistry registry, Configuration configuration) {
        this.registry = registry;

        logRegistry = new ReportingLoggingMeterRegistry(configuration);
        registry.add(logRegistry);

        if (configuration.isJmxEnable()) {
            MetricRegistry metricRegistry = new MetricRegistry();

            JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry)
                                                 .inDomain(JmxConfig.DEFAULT.domain())
                                                 .convertRatesTo(configuration.getRateUnit())
                                                 .convertDurationsTo(configuration.getDurationUnit())
                                                 .build();

            registry.add(new JmxMeterRegistry(JmxConfig.DEFAULT, Clock.SYSTEM, HierarchicalNameMapper.DEFAULT, metricRegistry, jmxReporter));
        }
    }

    @Override
    public Timer getTimer(String name) {
        return getTimer(name, NO_TAGS);
    }

    @Override
    public Timer getTimer(String name, Tag... tags) {
        return new TimerImpl(io.micrometer.core.instrument.Timer.start(registry), registry.timer(name, toTags(tags)));
    }

    @Override
    public Counter getCounter(String counter) {
        return getCounter(counter, NO_TAGS);
    }

    @Override
    public Counter getCounter(String counter, Tag... tags) {
        Tags counterTags = toTags(tags);

        return new CounterImpl(counters.computeIfAbsent(
                new CounterKey(counter, counterTags),
                key -> registry.gauge(key.name(), key.tags(), new AtomicLong())
        ));
    }

    @Override
    public Meter getMeter(String name) {
        return getMeter(name, NO_TAGS);
    }

    @Override
    public Meter getMeter(String name, Tag... tags) {
        return new MeterImpl(registry.counter(name, toTags(tags)));
    }

    @Override
    public <T extends Number> void registerGauge(String name, Gauge<T> gauge) {
        registerGauge(name, gauge, NO_TAGS);
    }

    @Override
    public <T extends Number> void registerGauge(String name, Gauge<T> gauge, Tag... tags) {
        io.micrometer.core.instrument.Gauge.builder(name, gauge, value -> value.getValue().doubleValue())
                                           .tags(toTags(tags))
                                           .strongReference(true)
                                           .register(registry);
    }

    /**
     * Log metrics of the last completed report period
     */
    @Override
    public void report() {
        logRegistry.report();
    }

    private static Tags toTags(Tag... tags) {
        return Tags.of(Arrays.stream(tags)
                             .map(tag -> io.micrometer.core.instrument.Tag.of(tag.key(), tag.value()))
                             .toList());
    }

    private record CounterKey(String name, Tags tags) {
    }
}
