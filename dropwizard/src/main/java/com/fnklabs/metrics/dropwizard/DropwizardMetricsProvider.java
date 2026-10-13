package com.fnklabs.metrics.dropwizard;

import com.fnklabs.metrics.Configuration;
import com.fnklabs.metrics.Metrics;
import com.fnklabs.metrics.MetricsProvider;

public class DropwizardMetricsProvider implements MetricsProvider {
    @Override
    public Metrics create(Configuration configuration) {
        return new DropwizardMetrics(configuration);
    }
}
