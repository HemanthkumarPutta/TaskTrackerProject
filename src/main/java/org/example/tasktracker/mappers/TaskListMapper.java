package org.example.tasktracker.mappers;

import org.example.tasktracker.domain.dto.TaskListDto;
import org.example.tasktracker.domain.entity.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
