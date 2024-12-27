package org.example.tasktracker.domain.dto;

import java.io.Serializable;

public record ErrorResponse(int status, String message, String details) {
}
