package com.example.TMS.mapper;

import com.example.TMS.dto.request.CommentAddRequest;
import com.example.TMS.dto.response.CommentAddResponse;
import com.example.TMS.dto.response.CommentDeleteResponse;
import com.example.TMS.dto.response.CommentDto;
import com.example.TMS.dto.response.InfoTaskResponse;
import com.example.TMS.entity.Comment;
import com.example.TMS.entity.Task;
import com.example.TMS.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "taskUuid", source = "comment.task.uuid")
    @Mapping(target = "commentUuid", source = "comment.uuid")
    CommentAddResponse toModel(Comment comment);

    @Mapping(target = "commentUuid", source = "uuid")
    CommentDeleteResponse toModelDelete(Comment comment);

    @Mapping(target = "description", source = "commentAddRequest.description")
    Comment toEntity(CommentAddRequest commentAddRequest, Task task, User user);

    @Mapping(target = "commentAuthor", source = "comment.user.email")
    @Mapping(target = "commentUuid", source = "comment.uuid")
    CommentDto mapToCommentDto(Comment comment);

    @Mapping(target = "commentAuthor", source = "comment.user.email")
    @Mapping(target = "commentUuid", source = "comment.uuid")
    List<CommentDto> mapToCommentDtos(List<Comment> comments);
}
