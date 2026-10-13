package com.fnklabs.metrics;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.text.MessageFormat;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;

public class MetricsFactory {
    private static final Configuration configuration;
    private static final Metrics METRICS_BINDER;

    static {
        Config config = ConfigFactory.load("metrics");

        configuration = new Configuration(
                config.hasPath("ratesUnit") ? TimeUnit.valueOf(config.getString("ratesUnit")) : TimeUnit.SECONDS,
                config.hasPath("durationUnit") ? TimeUnit.valueOf(config.getString("durationUnit")) : TimeUnit.MICROSECONDS,
                config.hasPath("report.period") ? config.getInt("report.period") : 5,
                config.hasPath("report.periodUnit") ? TimeUnit.valueOf(config.getString("report.periodUnit")) : TimeUnit.SECONDS,
                config.hasPath("jmx.enable") ? config.getBoolean("jmx.enable") : false
        );

        METRICS_BINDER = loadProvider().create(configuration);
    }

    public static Metrics getMetrics() {
        return METRICS_BINDER;
    }

    private static MetricsProvider loadProvider() {
        List<MetricsProvider> providers = ServiceLoader.load(MetricsProvider.class, MetricsFactory.class.getClassLoader())
                                                       .stream()
                                                       .map(ServiceLoader.Provider::get)
                                                       .toList();

        if (providers.isEmpty()) {
            throw new CanNotFindOrCreateMetricsBinder(
                    MessageFormat.format("No {0} found on the classpath, add one of metrics implementation modules", MetricsProvider.class.getName())
            );
        }

        if (providers.size() > 1) {
            throw new MultipleBindersException(providers);
        }

        return providers.getFirst();
    }
}
