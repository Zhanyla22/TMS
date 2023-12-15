package com.example.TMS.controller;

import com.example.TMS.dto.response.CommentDeleteResponse;
import com.example.TMS.entity.Comment;
import com.example.TMS.entity.Task;
import com.example.TMS.entity.User;
import com.example.TMS.enums.Priority;
import com.example.TMS.enums.Role;
import com.example.TMS.enums.Status;
import com.example.TMS.enums.StatusTask;
import com.example.TMS.service.CommentService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this); // Инициализация моков перед каждым тестом
    }

    @Test
    void add() throws Exception {

        User mockUser = new User("Zhanylai", "Mamytova", "ja.mamytova@gmail.com", "$2a$12$DQ6JKTJ4B1ukxka/8man5ee3MrcEIrsMw5vvViAkP450zJbvb8h7y", Role.ROLE_ADMIN);
        Task mockTask = new Task("CRUD for Task", "Create update read delete for task", Priority.HIGH, StatusTask.PENDING, mockUser, mockUser);
    }

    @Test
    void delete() throws Exception {
        UUID commentUuid = UUID.randomUUID();
        UUID userUuid = UUID.randomUUID();
        User mockUser = new User("Zhanylai", "Mamytova", "ja.mamytova@gmail.com", "$2a$12$DQ6JKTJ4B1ukxka/8man5ee3MrcEIrsMw5vvViAkP450zJbvb8h7y", Role.ROLE_ADMIN);
        mockUser.setId(2L);
        mockUser.setUuid(userUuid);
        mockUser.setStatus(Status.ACTIVE);
        Task mockTask = new Task("CRUD", "hello crud", Priority.HIGH, StatusTask.PENDING, mockUser, mockUser);
        Comment mockComment = new Comment();
        mockComment.setUuid(commentUuid);
        mockComment.setDescription("hello, brother");
        mockComment.setStatus(Status.ACTIVE);
        mockComment.setCreatedDate(LocalDateTime.now());
        mockComment.setId(1L);
        mockComment.setTask(mockTask);
        mockComment.setUser(mockUser);

        CommentDeleteResponse expectedResponse = CommentDeleteResponse.builder()
                .commentUuid(commentUuid)
                .status(Status.DELETED)
                .build();

        Mockito.when(commentService.deleteComment(commentUuid, mockComment.getUser())).thenReturn(expectedResponse);

        ResponseEntity<CommentDeleteResponse> responseEntity = commentController.delete(commentUuid, mockComment.getUser());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }


    private CommentDeleteResponse createMockDeleteResponse(UUID commentUuid) {
        return CommentDeleteResponse.builder()
                .commentUuid(commentUuid)
                .status(Status.DELETED)
                .build();
    }
}