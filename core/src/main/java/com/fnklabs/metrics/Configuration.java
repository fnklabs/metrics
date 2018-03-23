package com.fnklabs.metrics;

import java.util.concurrent.TimeUnit;

class Configuration {
    private final TimeUnit rateUnit;
    private final TimeUnit durationUnit;
    private final int reportPeriod;
    private final TimeUnit reportPeriodUnit;

    private final boolean jmxEnable;

    Configuration(TimeUnit rateUnit, TimeUnit durationUnit, int reportPeriod, TimeUnit reportPeriodUnit, boolean jmxEnable) {
        this.rateUnit = rateUnit;
        this.durationUnit = durationUnit;
        this.reportPeriod = reportPeriod;
        this.reportPeriodUnit = reportPeriodUnit;
        this.jmxEnable = jmxEnable;
    }

    public TimeUnit getRatesUnit() {
        return rateUnit;
    }

    public TimeUnit getDurationUnit() {
        return durationUnit;
    }

    public int getReportPeriod() {
        return reportPeriod;
    }

    public TimeUnit getReportPeriodUnit() {
        return reportPeriodUnit;
    }

    public boolean isJmxEnable() {
        return jmxEnable;
    }

    public TimeUnit getRateUnit() {
        return rateUnit;
    }
}
