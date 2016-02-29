package com.fnklabs.metrics;

class CounterImp implements Counter {
    private final com.codahale.metrics.Counter counter;

    public CounterImp(com.codahale.metrics.Counter counter) {
        this.counter = counter;
    }

    @Override
    public void inc() {
        counter.inc();
    }

    @Override
    public void inc(long n) {
        counter.inc(n);
    }

    @Override
    public void dec() {
        counter.dec();
    }

    @Override
    public void dec(long n) {
        counter.dec(n);
    }

    @Override
    public String toString() {
        return String.valueOf(counter.getCount());
    }
}
