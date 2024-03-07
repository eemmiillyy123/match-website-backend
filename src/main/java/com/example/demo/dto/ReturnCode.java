package com.example.demo.dto;

public enum ReturnCode {
	FAIL(0), SUCC(1);

    private int value;

    private ReturnCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
