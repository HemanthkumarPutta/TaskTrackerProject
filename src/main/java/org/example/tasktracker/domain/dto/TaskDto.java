package org.example.tasktracker.domain.dto;

import org.example.tasktracker.domain.entity.TaskPriority;
import org.example.tasktracker.domain.entity.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {

}
