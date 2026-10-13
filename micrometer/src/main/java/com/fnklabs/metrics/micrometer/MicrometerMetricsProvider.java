package com.fnklabs.metrics.micrometer;

import com.fnklabs.metrics.Configuration;
import com.fnklabs.metrics.Metrics;
import com.fnklabs.metrics.MetricsProvider;

public class MicrometerMetricsProvider implements MetricsProvider {
    @Override
    public Metrics create(Configuration configuration) {
        return new MicrometerMetrics(configuration);
    }
}
