package com.fnklabs.metrics;

import java.util.Objects;

/**
 * Metric tag (label) - key/value pair that adds dimension to metric.
 * <p>
 * Implementations that don't support dimensional metrics (e.g. dropwizard) ignore tags.
 *
 * @param key   Tag key
 * @param value Tag value
 */
public record Tag(String key, String value) {
    public Tag {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(value, "value");
    }

    /**
     * Create tag
     *
     * @param key   Tag key
     * @param value Tag value
     *
     * @return Tag instance
     */
    public static Tag of(String key, String value) {
        return new Tag(key, value);
    }
}
