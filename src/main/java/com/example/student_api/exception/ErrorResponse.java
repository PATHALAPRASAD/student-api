package com.example.student_api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @SuppressWarnings("unused")
    private Integer errorCode;

    @SuppressWarnings("unused")
    private String errorMessage;

    @SuppressWarnings("unused")
    private List<String> errorMessages;

    public LocalDateTime getTimestamp() {
        return LocalDateTime.now();
    }
}
