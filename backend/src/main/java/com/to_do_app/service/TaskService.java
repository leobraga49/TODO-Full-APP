package com.to_do_app.service;

import com.to_do_app.model.Task;
import com.to_do_app.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public Task update(Long id, Task task) {
        Task taskToUpdate = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setCompleted(task.isCompleted());
        return taskRepository.save(taskToUpdate);
    }
}
