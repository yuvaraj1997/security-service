package com.yuvaraj.security.helpers;

public enum TokenType {

    REFRESH("REFRESH"),
    SESSION("SESSION");

    final String type;

    TokenType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
