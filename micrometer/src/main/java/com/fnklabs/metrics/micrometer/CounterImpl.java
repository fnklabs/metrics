package com.fnklabs.metrics.micrometer;

import com.fnklabs.metrics.Counter;

import java.util.concurrent.atomic.AtomicLong;

class CounterImpl implements Counter {
    private final AtomicLong counter;

    CounterImpl(AtomicLong counter) {
        this.counter = counter;
    }

    @Override
    public void inc() {
        counter.incrementAndGet();
    }

    @Override
    public void inc(long n) {
        counter.addAndGet(n);
    }

    @Override
    public void dec() {
        counter.decrementAndGet();
    }

    @Override
    public void dec(long n) {
        counter.addAndGet(-n);
    }

    @Override
    public String toString() {
        return String.valueOf(counter.get());
    }
}
