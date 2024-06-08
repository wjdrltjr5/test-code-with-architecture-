package com.example.demo.mock;

import com.example.demo.common.service.port.UuidHolder;
import lombok.RequiredArgsConstructor;


public class TestUuidHolder implements UuidHolder {
    private final String uuid;

    public TestUuidHolder(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String random() {
        return uuid;
    }
}
