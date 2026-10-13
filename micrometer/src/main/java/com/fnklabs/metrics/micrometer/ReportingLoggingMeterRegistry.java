package com.fnklabs.metrics.micrometer;

import com.fnklabs.metrics.Configuration;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * {@link LoggingMeterRegistry} that publishes with configured report period and allows to publish on demand
 */
class ReportingLoggingMeterRegistry extends LoggingMeterRegistry {
    private final TimeUnit durationUnit;

    ReportingLoggingMeterRegistry(Configuration configuration) {
        super(config(configuration), Clock.SYSTEM);

        this.durationUnit = configuration.getDurationUnit();
    }

    void report() {
        publish();
    }

    @Override
    protected TimeUnit getBaseTimeUnit() {
        // invoked from the super constructor before durationUnit is assigned
        return durationUnit == null ? super.getBaseTimeUnit() : durationUnit;
    }

    private static LoggingRegistryConfig config(Configuration configuration) {
        Duration step = Duration.of(configuration.getReportPeriod(), configuration.getReportPeriodUnit().toChronoUnit());

        return new LoggingRegistryConfig() {
            @Override
            public String get(String key) {
                return null;
            }

            @Override
            public Duration step() {
                return step;
            }
        };
    }
}
