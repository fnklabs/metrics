package com.fnklabs.metrics;

/**
 * Service provider interface for {@link Metrics} implementations.
 * <p>
 * Implementations are discovered by {@link MetricsFactory} through {@link java.util.ServiceLoader}, so every
 * implementation module must register its provider in {@code META-INF/services/com.fnklabs.metrics.MetricsProvider}.
 */
public interface MetricsProvider {
    /**
     * Create metrics implementation
     *
     * @param configuration Metrics configuration loaded from {@code metrics.conf}
     *
     * @return Metrics instance
     */
    Metrics create(Configuration configuration);
}
