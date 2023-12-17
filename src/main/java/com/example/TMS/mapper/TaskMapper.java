package com.example.TMS.mapper;

import com.example.TMS.dto.request.TaskAddRequest;
import com.example.TMS.dto.response.ExecutorInfoResponse;
import com.example.TMS.dto.response.TaskInfoMiniResponse;
import com.example.TMS.dto.response.InfoTaskResponse;
import com.example.TMS.dto.response.TaskDeleteResponse;
import com.example.TMS.entity.Comment;
import com.example.TMS.entity.Task;
import com.example.TMS.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

/**
 * использовала MapStruct для чистоты и сокращения кода
 */
@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface TaskMapper {

    @Mapping(target = "commentResponses", source = "comments")
    InfoTaskResponse toModels(Task task, List<Comment> comments);

    InfoTaskResponse toModel(Task task);

    TaskDeleteResponse toModelDelete(Task task);

    TaskInfoMiniResponse toModelInfo(Task task);

    ExecutorInfoResponse toModelExecutor(Task task);

    @Mapping(target = "status", constant = "ACTIVE")
    Task toEntity(TaskAddRequest taskAddRequest, User author, User executor, UUID uuid);

    @Mapping(target = "commentResponses", source = "comments")
    List<InfoTaskResponse> toModelList(Page<Task> tasks);
}
