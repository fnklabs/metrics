package com.fnklabs.metrics;

import java.util.concurrent.TimeUnit;

class Configuration {
    private final TimeUnit rateUnit;
    private final TimeUnit durationUnit;
    private final int reportPeriod;
    private final TimeUnit reportPeriodUnit;

    Configuration(TimeUnit rateUnit, TimeUnit durationUnit, int reportPeriod, TimeUnit reportPeriodUnit) {
        this.rateUnit = rateUnit;
        this.durationUnit = durationUnit;
        this.reportPeriod = reportPeriod;
        this.reportPeriodUnit = reportPeriodUnit;
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
}
