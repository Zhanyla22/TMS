package com.example.TMS.service;

import com.example.TMS.dto.request.CommentAddRequest;
import com.example.TMS.dto.response.CommentAddResponse;
import com.example.TMS.entity.User;

import java.util.UUID;

public interface CommentService {

    CommentAddResponse addComment(CommentAddRequest commentAddRequest, UUID taskUuid, User user);
}
