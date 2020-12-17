package com.example;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class CartPOJO {

    private Integer sessionId;

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }
}
