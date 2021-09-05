package com.io.framey.datamodel;

import java.util.HashMap;
import java.util.Map;

public enum Position {
    ENGINEER(1L),
    MANAGER(2L),
    CEO(3L),
    SALESMAN(4L);

    private Long positionId;

    Position(Long positionId) {
        this.positionId = positionId;
    }

    public long getPositionValueId() {
        return positionId;
    }

    private static final Map<Long, Position> TYPES_BY_ID = new HashMap<>();

    static {
        for (Position value : Position.values()) {
            TYPES_BY_ID.put(value.positionId, value);
        }
    }
    public static Position valueOf(long listValueId) {
        return TYPES_BY_ID.get(listValueId);
    }

}
