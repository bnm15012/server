package com.dancestudio.erp.enums;

public enum AccessLevel {
    NONE(0),
    FULL(1);

    private final int code;

    AccessLevel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static AccessLevel fromCode(int code) {
        for (AccessLevel level : values()) {
            if (level.code == code)
                return level;
        }
        return NONE;
    }
}
