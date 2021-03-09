package com.university.semanticnetwork.model;

import java.util.HashMap;
import java.util.Map;

public enum RelationType {
    NO_INHERIT(0),
    IS_A(1),
    HAS_PART(2);

    private final static Map<Integer, RelationType> CONSTANTS = new HashMap<>();

    static {
        for (RelationType relationType : values()) {
            CONSTANTS.put(relationType.value, relationType);
        }
    }

    private final Integer value;

    RelationType(Integer value) {
        this.value = value;
    }

    public static RelationType fromValue(Integer value) {
        if (value != null) {
            return CONSTANTS.getOrDefault(value, NO_INHERIT);
        }

        return null;
    }

    public Integer getValue() {
        return this.value;
    }
}
