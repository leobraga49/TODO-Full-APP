package com.to_do_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.to_do_app.model.Task;
import com.to_do_app.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Task> tasks;

    @BeforeEach
    void setUp() {
        tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Test task 1", "Test description 1", false));
        tasks.add(new Task(2L, "Test task 2", "Test description 2", false));
    }

    @Test
    void getAllTasks() throws Exception {
        when(taskService.findAll()).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(tasks)));
    }

    @Test
    void getTaskById() throws Exception {
        when(taskService.findById(1L)).thenReturn(tasks.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(tasks.get(0))));
    }


    @Test
    void createTask() throws Exception {
        Task task = new Task();
        task.setTitle("Test task");
        task.setDescription("Test description");
        task.setCompleted(false);

        when(taskService.save(task)).thenReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(task)));
    }


    @Test
    void updateTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test task");
        task.setDescription("Test description");
        task.setCompleted(false);

        when(taskService.update(1L, task)).thenReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/tasks/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(task)));
    }

    @Test
    void deleteTask() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tasks/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(taskService).deleteById(1L);

    }

}