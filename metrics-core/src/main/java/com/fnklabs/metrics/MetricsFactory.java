package com.fnklabs.metrics;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

public class MetricsFactory {
    private static final String BINDER_CLASS_NAME = "com.fnklabs.metrics.MetricsBinder";
    private static Metrics METRICS_BINDER;
    private static String STATIC_BINDER_PATH = "com/fnklabs/MetricsBinder.class";

    public static Metrics getMetrics() {
        return METRICS_BINDER;
    }

    private static Set<URL> findPossibleStaticBinderPathSet() {
        Set<URL> staticLoggerBinderPathSet = new LinkedHashSet<URL>();

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

    static {
        Set<URL> possibleStaticBinderPathSet = findPossibleStaticBinderPathSet();

        if (possibleStaticBinderPathSet.size() > 1) {
            throw new MultipleBindersException();
        }

        try {
            Class<Metrics> metricsClass = (Class<Metrics>) Class.forName(BINDER_CLASS_NAME);
            METRICS_BINDER = metricsClass.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new CanNotFindOrCreateMetricsBinder(e);
        }
    }
}
