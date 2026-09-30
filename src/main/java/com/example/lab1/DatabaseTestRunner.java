package com.example.lab1;

import com.example.lab1.entity.Task;
import com.example.lab1.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

// Лаба 3: простой тест взаимодействия с базой данных (добавление и извлечение задачи)
@Component
public class DatabaseTestRunner implements CommandLineRunner {

    private final TaskRepository taskRepository;

    public DatabaseTestRunner(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) {
        Task saved = taskRepository.save(Task.builder()
                .name("DB test task")
                .description("Проверка сохранения в БД")
                .dueDate(LocalDate.now())
                .completed(false)
                .build());
        System.out.println("[DB TEST] Добавлена задача с id = " + saved.getId());

        Task found = taskRepository.findById(saved.getId()).orElse(null);
        System.out.println("[DB TEST] Извлечена задача: " + (found != null ? found.getName() : "не найдена"));

        taskRepository.deleteById(saved.getId());
        System.out.println("[DB TEST] Тестовая задача удалена. Всего задач в БД: " + taskRepository.count());
    }
}
