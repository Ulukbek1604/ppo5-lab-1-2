package com.example.lab1.service;

import com.example.lab1.entity.Task;
import com.example.lab1.model.TaskModel;
import com.example.lab1.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskModel> findAll() {
        return taskRepository.findAll().stream().map(this::convertToModel).collect(Collectors.toList());
    }

    public TaskModel findById(Long id) {
        return convertToModel(taskRepository.findById(id).orElse(null));
    }

    public Task save(TaskModel task) {
        Task entity = convertToEntity(task);
        return taskRepository.save(entity);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskModel convertToModel(Task task) {
        if (task == null) {
            return null;
        }
        return TaskModel.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .completed(task.isCompleted())
                .build();
    }

    private Task convertToEntity(TaskModel taskModel) {
        if (taskModel == null) {
            return null;
        }
        return Task.builder()
                .id(taskModel.getId())
                .name(taskModel.getName())
                .description(taskModel.getDescription())
                .dueDate(taskModel.getDueDate())
                .completed(taskModel.isCompleted())
                .build();
    }
}
