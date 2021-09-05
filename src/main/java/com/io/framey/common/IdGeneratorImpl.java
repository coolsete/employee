package com.io.framey.common;

import java.util.concurrent.atomic.AtomicLong;

public class IdGeneratorImpl implements IdGenerator {

    private AtomicLong currentId = new AtomicLong(0L);

    @Override
    public long generateId() {
        return currentId.incrementAndGet();
    }

    @Override
    public void clear() {
        currentId  = new AtomicLong(0L);
    }
}
