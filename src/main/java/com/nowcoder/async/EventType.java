package com.nowcoder.async;

/**
 * @Date: 18-9-5
 * @version： V1.0
 * @Author: Chandler
 * @Description: ${todo}
 */
public enum EventType {
    LOGIN(0),
    COMMENT(1);
    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
