package com.fnklabs.metrics;

import java.util.List;

public class MultipleBindersException extends RuntimeException {
    public MultipleBindersException(List<MetricsProvider> providers) {
        super("Multiple metrics providers found on the classpath: %s".formatted(providers.stream()
                                                                                         .map(provider -> provider.getClass().getName())
                                                                                         .toList()));
    }
}
