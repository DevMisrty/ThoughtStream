package com.practice.thoughtstream.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

    private LocalDateTime time;
    private String message;
    private HttpStatus status;
    private T data;

    public ApiResponse(T data, HttpStatus status, String message) {
        this.time = LocalDateTime.now();
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public static <T> ResponseEntity<ApiResponse<T>> response(
            HttpStatus status,
            String message,
            T data
    ) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponse<>(data, status, message));
    }
}
