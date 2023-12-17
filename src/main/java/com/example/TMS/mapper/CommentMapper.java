package com.example.TMS.mapper;

import com.example.TMS.dto.request.CommentAddRequest;
import com.example.TMS.dto.response.CommentAddResponse;
import com.example.TMS.dto.response.CommentDeleteResponse;
import com.example.TMS.dto.response.CommentResponse;
import com.example.TMS.entity.Comment;
import com.example.TMS.entity.Task;
import com.example.TMS.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "taskUuid", source = "comment.task.uuid")
    @Mapping(target = "commentUuid", source = "comment.uuid")
    CommentAddResponse toModel(Comment comment);

    @Mapping(target = "commentUuid", source = "uuid")
    CommentDeleteResponse toModelDelete(Comment comment);

    @Mapping(target = "description", source = "commentAddRequest.description")
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "status", constant = "ACTIVE")
    Comment toEntity(CommentAddRequest commentAddRequest, Task task, User user, UUID uuid);

    @Mapping(target = "commentAuthor", source = "comment.user.email")
    @Mapping(target = "commentUuid", source = "comment.uuid")
    CommentResponse mapToCommentDto(Comment comment);

    @Mapping(target = "commentAuthor", source = "comment.user.email")
    @Mapping(target = "commentUuid", source = "comment.uuid")
    List<CommentResponse> mapToCommentDtos(List<Comment> comments);
}
