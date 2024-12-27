package org.example.tasktracker.mappers;

import org.example.tasktracker.domain.dto.TaskDto;
import org.example.tasktracker.domain.entity.Task;

public interface TaskMapper {
    Task fromDto(TaskDto dto);

    TaskDto toDto(Task task);
}
