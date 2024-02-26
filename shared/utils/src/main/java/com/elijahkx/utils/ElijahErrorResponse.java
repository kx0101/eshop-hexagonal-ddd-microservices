package com.elijahkx.utils;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElijahErrorResponse {
    private LocalDateTime timestamp;

    private int status;

    private String error;

    private List<String> messages;
}
