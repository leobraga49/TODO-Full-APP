package com.to_do_app.service;

import com.to_do_app.model.Task;
import com.to_do_app.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(taskRepository);
    }

    @Test
    @DisplayName("findById should return the task with the specified id")
    void findById_ThenReturnTask() {
        // Arrange
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test task");
        task.setDescription("Test description");
        task.setCompleted(false);

        when(taskRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(task));

        // Act
        Task returnedTask = taskService.findById(1L);

        // Assert
        assertSame(task, returnedTask);
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void whenSaveTask_ThenReturnSavedTask() {
        // Arrange
        Task task = new Task();
        task.setTitle("Test task");
        task.setDescription("Test description");
        task.setCompleted(false);

        when(taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(task);

        // Act
        Task returnedTask = taskService.save(task);

        // Assert
        assertEquals(task, returnedTask);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void findAll() {
        // Arrange
        Task task = new Task();
        task.setTitle("Test task");
        task.setDescription("Test description");
        task.setCompleted(false);

        when(taskRepository.findAll()).thenReturn(List.of(task));

        // Act
        List<Task> returnedTasks = taskService.findAll();

        //Assert
        assertEquals(1, returnedTasks.size());
        verify(taskRepository, times(1)).findAll();

    }







}