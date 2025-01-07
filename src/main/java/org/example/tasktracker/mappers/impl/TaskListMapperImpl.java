package org.example.tasktracker.mappers.impl;

import org.example.tasktracker.domain.dto.TaskListDto;
import org.example.tasktracker.domain.entity.Task;
import org.example.tasktracker.domain.entity.TaskList;
import org.example.tasktracker.domain.entity.TaskStatus;
import org.example.tasktracker.mappers.TaskListMapper;
import org.example.tasktracker.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }


    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(taskListDto.id(), taskListDto.title(), taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks()).
                        map(tasks -> tasks.stream().map(taskMapper::fromDto).toList()).
                        orElse(null),null,null);
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(
             taskList.getId(),
             taskList.getTitle(),
             taskList.getDescription(),
             Optional.ofNullable(taskList.getTasks()).map(List::size).orElse(0),
             calculateTaskListProgress(taskList.getTasks()),
             Optional.ofNullable(taskList.getTasks())
                .map(tasks -> tasks.stream().map(taskMapper::toDto).toList()).orElse(null)
        );
    }

    private Double calculateTaskListProgress(List<Task> tasks){
        if(tasks.isEmpty())return null;
        long closed = tasks.stream().filter(task->
                TaskStatus.CLOSE == task.getStatus()
        ).count();
        return (double) (closed/tasks.size());
    }
}
