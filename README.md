# metrics

Utils for metrics

Requires Java 25.

# Modules

| Module               | Description                                                                                                                                      |
|----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| `metrics-core`       | API (`Metrics`, `Timer`, `Counter`, `Meter`, `Gauge`, `Tag`) and `MetricsFactory`                                                                |
| `metrics-dropwizard` | [Dropwizard Metrics](https://metrics.dropwizard.io) implementation (tags are ignored)                                                            |
| `metrics-micrometer` | [Micrometer](https://micrometer.io) implementation with tags support, registers meters in `io.micrometer.core.instrument.Metrics.globalRegistry` |

Add exactly one implementation module to the classpath. `MetricsFactory` discovers it with `java.util.ServiceLoader`
through the `com.fnklabs.metrics.MetricsProvider` SPI, so a custom implementation only needs to register its provider in
`META-INF/services/com.fnklabs.metrics.MetricsProvider`.

# Installation

Artifacts are published to Maven Central under the `com.fnklabs` group. Add one implementation module, `metrics-core`
comes with it transitively.

Gradle (Kotlin DSL) `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fnklabs.metrics:metrics-micrometer:1.0.0")
    // or
    // implementation("com.fnklabs:metrics-dropwizard:0.1")
}
```


Gradle version catalog `gradle/libs.versions.toml`:

```toml
[versions]
fnklabs-metrics = "1.0.0"

[libraries]
fnklabs-metrics-core = { module = "com.fnklabs.metrics:metrics-core", version.ref = "fnklabs-metrics" }
fnklabs-metrics-micrometer = { module = "com.fnklabs.metrics:metrics-micrometer", version.ref = "fnklabs-metrics" }
fnklabs-metrics-dropwizard = { module = "com.fnklabs.metrics:metrics-dropwizard", version.ref = "fnklabs-metrics" }
```

```kotlin
dependencies {
    implementation(libs.fnklabs.metrics.micrometer)
}
```

A library that only codes against the API can depend on `metrics-core` alone and leave the choice of implementation
to the application.

# Usage

## Timer

```java
import com.fnklabs.metrics.Timer;
import com.fnklabs.metrics.Counter;

public class Test {

    public void main(String[] args) {
        Metrics metrics = MetricsFactory.getMetrics();

        try (Timer testTimer = metrics.getTimer("test_timer")) {
            // code
        }
    }
}
```

## Counter

```java
import com.fnklabs.metrics.Timer;
import com.fnklabs.metrics.Counter;

public class Test {

    public void main(String[] args) {
        Metrics metrics = MetricsFactory.getMetrics();

        Counter testCounter = metrics.getCounter("test_counter");
        testCounter.inc();
    }
}
```

## Gauge

```java
import com.fnklabs.metrics.Timer;
import com.fnklabs.metrics.Counter;

public class Test {

    public void main(String[] args) {
        Metrics metrics = MetricsFactory.getMetrics();

        metrics.registerGauge("test_gauge", new Gauge<Number>() {
            @Override
            public Number getValue() {
                return System.currentTimeMillis();
            }
        });
    }
}
```

## Meter

```java
import com.fnklabs.metrics.Timer;
import com.fnklabs.metrics.Counter;

public class Test {

    public void main(String[] args) {
        Metrics metrics = MetricsFactory.getMetrics();

        Meter meter = metrics.getMeter("test_meter");

        meter.inc();
    }
}
```

## Tags

Every metric has an overload that accepts tags (labels). Implementations without dimensional metrics support (dropwizard) ignore tags.

```java
import com.fnklabs.metrics.Tag;

public class Test {

    public void main(String[] args) {
        Metrics metrics = MetricsFactory.getMetrics();

        try (Timer timer = metrics.getTimer("http_request", Tag.of("method", "GET"), Tag.of("status", "200"))) {
            // code
        }

        metrics.getCounter("queue_size", Tag.of("queue", "orders")).inc();
        metrics.getMeter("requests", Tag.of("status", "error")).inc();
        metrics.registerGauge("pool_size", () -> 10, Tag.of("pool", "main"));
    }
}
```

# Reporting

Metrics by default report through slf4j with concrete period that defined in config file or available from JMX

## Configuration

Configuration file `metrics.conf` must be available from class must

```json

{
  ratesUnit: "SECONDS",
  durationUnit: "MILLISECONDS",
  report: {
    periodUnit: "SECONDS",
    period: 60
  },
  jmx: {
    enable: true
  }
}

```

