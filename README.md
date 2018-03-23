# metrics
Utils for metrics

# Usage


## Timer 
```java
import com.fnklabs.metrics.Timer;
import com.fnklabs.metrics.Counter;

public class Test {

    public void main(String[] args) {
        Metrics metrics = MetricsFactory.getMetrics();
        
        try (Timer testTimer = metrics.getTimer("test_timer") ) {
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

