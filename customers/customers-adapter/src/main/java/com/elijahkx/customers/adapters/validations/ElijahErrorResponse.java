package com.elijahkx.customers.adapters.validations;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ElijahErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private List<String> messages;

    public ElijahErrorResponse(HttpStatus status, List<String> messages) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.messages = messages;
    }

    public ElijahErrorResponse(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.messages = Collections.singletonList(message);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public List<String> getMessages() {
        return messages;
    }
}
