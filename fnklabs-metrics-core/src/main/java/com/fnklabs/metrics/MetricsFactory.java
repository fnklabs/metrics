package com.fnklabs.metrics;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MetricsFactory {
    private static final String BINDER_CLASS_NAME = "com.fnklabs.metrics.MetricsBinder";
    private static final Configuration configuration;
    private static Metrics METRICS_BINDER;
    private static String STATIC_BINDER_PATH = "com/fnklabs/MetricsBinder.class";

    static {
        Config config = ConfigFactory.load("metrics");

        configuration = new Configuration(
                config.hasPath("ratesUnit") ? TimeUnit.valueOf(config.getString("ratesUnit")) : TimeUnit.SECONDS,
                config.hasPath("durationUnit") ? TimeUnit.valueOf(config.getString("durationUnit")) : TimeUnit.MICROSECONDS,
                config.hasPath("report.period") ? config.getInt("report.period") : 5,
                config.hasPath("report.periodUnit") ? TimeUnit.valueOf(config.getString("report.periodUnit")) : TimeUnit.SECONDS
        );

        Set<URL> possibleStaticBinderPathSet = findPossibleStaticBinderPathSet();

        if (possibleStaticBinderPathSet.size() > 1) {
            throw new MultipleBindersException();
        }

        try {
            Class<Metrics> metricsClass = (Class<Metrics>) Class.forName(BINDER_CLASS_NAME);

            METRICS_BINDER = metricsClass.getDeclaredConstructor(Configuration.class).newInstance(configuration);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new CanNotFindOrCreateMetricsBinder(e);
        }
    }

    public static Metrics getMetrics() {
        return METRICS_BINDER;
    }

    private static Set<URL> findPossibleStaticBinderPathSet() {
        Set<URL> staticLoggerBinderPathSet = new LinkedHashSet<>();

        try {
            ClassLoader loggerFactoryClassLoader = MetricsFactory.class.getClassLoader();
            Enumeration<URL> paths;

            if (loggerFactoryClassLoader == null) {
                paths = ClassLoader.getSystemResources(STATIC_BINDER_PATH);
            } else {
                paths = loggerFactoryClassLoader.getResources(STATIC_BINDER_PATH);
            }

            while (paths.hasMoreElements()) {
                URL path = paths.nextElement();
                staticLoggerBinderPathSet.add(path);
            }
        } catch (IOException ioe) {
        }

        return staticLoggerBinderPathSet;
    }
}
