package com.example.TMS.controller;

import com.example.TMS.dto.request.AuthRequest;
import com.example.TMS.dto.request.TaskAddRequest;
import com.example.TMS.entity.Task;
import com.example.TMS.entity.User;
import com.example.TMS.enums.Priority;
import com.example.TMS.enums.Status;
import com.example.TMS.enums.StatusTask;
import com.example.TMS.repository.TaskRepository;
import com.example.TMS.repository.UsersRepository;
import com.example.TMS.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
@Order(3)
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AuthService authService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TaskRepository taskRepository;

    @Test
    void add() throws Exception {
        TaskAddRequest request = TaskAddRequest.builder()
                .statusTask(StatusTask.WAITING)
                .description("Test")
                .title("test")
                .priority(Priority.MEDIUM)
                .build();

        String json = objectMapper.writeValueAsString(request);
        String jwtToken = authService.auth(AuthRequest.builder()
                        .email("test@gmail.com")
                        .password("Password123!")
                        .build())
                .getJwt();

        mockMvc.perform(post("/task/add").contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void changeStatus() throws Exception {
        User user = usersRepository.findByEmail("test@gmail.com").orElse(null);
        UUID uuid = UUID.randomUUID();
        Task task = Task.builder()
                .status(Status.ACTIVE)
                .uuid(uuid)
                .statusTask(StatusTask.PENDING)
                .author(user)
                .build();

        taskRepository.saveAndFlush(task);

        String jwtToken = authService.auth(AuthRequest.builder()
                        .email("test@gmail.com")
                        .password("Password123!")
                        .build())
                .getJwt();

        mockMvc.perform(put("/task/change-status/" + task.getUuid()).contentType(MediaType.APPLICATION_JSON)
                        .param("statusTask", "FINISHED")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void getTaskByCurrentUser() throws Exception {
        String jwtToken = authService.auth(AuthRequest.builder()
                        .email("test@gmail.com")
                        .password("Password123!")
                        .build())
                .getJwt();
        mockMvc.perform(get("/task/get-all-active-created-current-user").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().is2xxSuccessful());

    }
}