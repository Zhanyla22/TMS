package com.example.TMS.controller;

import com.example.TMS.dto.request.AuthRequest;
import com.example.TMS.entity.Comment;
import com.example.TMS.entity.Task;
import com.example.TMS.entity.User;
import com.example.TMS.enums.Status;
import com.example.TMS.enums.StatusTask;
import com.example.TMS.repository.CommentRepository;
import com.example.TMS.repository.TaskRepository;
import com.example.TMS.repository.UsersRepository;
import com.example.TMS.service.AuthService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
@Order(4)
class CommentControllerTest {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    AuthService authService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void delete() throws Exception {
        User user = usersRepository.findByEmail("test@gmail.com").orElse(null);
        UUID uuid = UUID.randomUUID();
        Task task = Task.builder()
                .status(Status.ACTIVE)
                .uuid(uuid)
                .statusTask(StatusTask.PENDING)
                .author(user)
                .build();

        taskRepository.saveAndFlush(task);

        UUID uuid1 = UUID.randomUUID();
        Comment comment = Comment.builder()
                .description("test comment")
                .user(user)
                .task(task)
                .status(Status.ACTIVE)
                .uuid(uuid1)
                .build();

        commentRepository.saveAndFlush(comment);

        String jwtToken = authService.auth(AuthRequest.builder()
                        .email("test@gmail.com")
                        .password("Password123!")
                        .build())
                .getJwt();

        mockMvc.perform(put("/comment/delete/" + comment.getUuid()).contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().is2xxSuccessful());

    }
}