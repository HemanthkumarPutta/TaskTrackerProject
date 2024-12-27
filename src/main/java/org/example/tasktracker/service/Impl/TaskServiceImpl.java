package org.example.tasktracker.service.Impl;

import org.example.tasktracker.domain.entity.Task;
import org.example.tasktracker.domain.entity.TaskList;
import org.example.tasktracker.domain.entity.TaskPriority;
import org.example.tasktracker.domain.entity.TaskStatus;
import org.example.tasktracker.respository.TaskListRepository;
import org.example.tasktracker.respository.TaskRepository;
import org.example.tasktracker.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(null != task.getId()){
            throw new IllegalArgumentException("Task already exists");
        }
        if(null == task.getTitle() || task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task must have title");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).
                orElse(TaskPriority.Medium);
        TaskStatus taskStatus = TaskStatus.OPEN;
        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(()-> new IllegalArgumentException("Invalid TaskList id"));
        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskList,
                taskPriority,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        return taskRepository.save(taskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(null == task.getId()){
            throw new IllegalArgumentException("Task has no ID");
        }
        if(!Objects.equals(taskId, task.getId())){
            throw new IllegalArgumentException("Task IDs not matching");
        }

        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId).
                orElseThrow(()-> new IllegalArgumentException("Task not existing"));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(LocalDateTime.now());
        return taskRepository.save(existingTask);
    }

    @Transactional//DB consistency and cascade rollback as there are multiple DB calls
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
