package com.example.lab1.controller;

import com.example.lab1.model.TaskModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private List<TaskModel> tasks = new ArrayList<>();

    // Конструктор с некоторыми тестовыми данными
    public TaskController() {
        tasks.add(TaskModel.builder()
                .id(1L)
                .name("Test Task 1")
                .description("Description for Task 1")
                .dueDate(LocalDate.now())
                .completed(false)
                .build()
        );

        tasks.add(TaskModel.builder()
                .id(2L)
                .name("Test Task 2")
                .description("Description for Task 2")
                .dueDate(LocalDate.now().plusDays(1))
                .completed(true)
                .build()
        );
    }

    @GetMapping("/tasks")
    public String listTasks(Model model) {
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/task/new")
    public String newTask(Model model) {
        model.addAttribute("taskModel", new TaskModel());
        return "task-form";
    }

    @GetMapping("/task/edit/{id}")
    public String editTask(@PathVariable("id") Long id, Model model) {
        TaskModel task = tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (task == null) {
            return "redirect:/tasks";
        }
        model.addAttribute("taskModel", task);
        return "task-form";
    }

    @PostMapping("/task/save")
    public String saveTask(TaskModel taskModel) {
        // Здесь должен быть код для сохранения задачи
        // Для простоты, мы просто добавляем задачу в список
        if (taskModel.getId() == null) {
            taskModel.setId(tasks.stream().mapToLong(TaskModel::getId).max().orElse(0) + 1);
        } else {
            tasks.removeIf(t -> t.getId().equals(taskModel.getId()));
        }
        tasks.add(taskModel);
        return "redirect:/tasks";
    }
}
