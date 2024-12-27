package org.example.tasktracker.mappers.impl;

import org.example.tasktracker.domain.dto.TaskDto;
import org.example.tasktracker.domain.entity.Task;
import org.example.tasktracker.mappers.TaskMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public Task fromDto(TaskDto dto) {
        return new Task(dto.id(), dto.title(), dto.description(), dto.dueDate(),
                dto.status(), null, dto.priority(), null, null);
    }

    @Override
    public TaskDto toDto(Task task) {
        return new TaskDto(task.getId(),task.getTitle(), task.getDescription(),
                task.getDueDate(), task.getPriority(), task.getStatus());
    }
}
