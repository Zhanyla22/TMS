package com.example.TMS.service.impl;

import com.example.TMS.dto.request.CommentAddRequest;
import com.example.TMS.dto.response.CommentAddResponse;
import com.example.TMS.entity.Comment;
import com.example.TMS.entity.Task;
import com.example.TMS.entity.User;
import com.example.TMS.enums.Status;
import com.example.TMS.exception.common.TaskNotFoundException;
import com.example.TMS.mapper.CommentMapper;
import com.example.TMS.repository.CommentRepository;
import com.example.TMS.repository.TaskRepository;
import com.example.TMS.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {

    TaskRepository taskRepository;
    CommentRepository commentRepository;
    CommentMapper commentMapper;

    @Override
    public CommentAddResponse addComment(CommentAddRequest commentAddRequest,UUID taskUuid, User user) {
        Task task = taskRepository.findTaskByUuid(taskUuid).orElseThrow(
                () -> new TaskNotFoundException(taskUuid, HttpStatus.NOT_FOUND));
        Comment comment = commentMapper.toEntity(commentAddRequest, task, user);
        comment.setUuid(UUID.randomUUID());
        comment.setStatus(Status.ACTIVE);
        return commentMapper.toModel(commentRepository.save(comment));
    }
}
