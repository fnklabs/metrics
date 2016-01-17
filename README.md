# metrics
Metrics lib like slf4

# Usage

```java
import com.fnklabs.metrics.Timer;
import com.fnklabs.metrics.Counter;

public class Test {

    public void main(String[] args) {
        Metrics metrics = MetricsFactory.getMetrics();
        
        Timer testTimer = metrics.getTimer("test_timer");
        testTimer.stop();
        
        
        Counter testCounter = metrics.getCounter("test_counter");
        testCounter.inc();
    }
}
```