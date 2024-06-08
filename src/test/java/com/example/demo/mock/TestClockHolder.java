package com.example.demo.mock;

import com.example.demo.common.service.port.ClockHolder;

public class TestClockHolder implements ClockHolder {

    private final long mills;

    public TestClockHolder(long mills) {
        this.mills = mills;
    }

    @Override
    public long mills() {
        return mills;
    }
}
