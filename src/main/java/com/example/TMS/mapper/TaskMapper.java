package com.example.TMS.mapper;

import com.example.TMS.dto.request.AddTaskRequest;
import com.example.TMS.dto.response.*;
import com.example.TMS.entity.Comment;
import com.example.TMS.entity.Task;
import com.example.TMS.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * использовала MapStruct для чистоты и сокращения кода
 */
@Mapper(componentModel = "spring",uses = CommentMapper.class)
public interface TaskMapper {

    @Mapping(target = "commentDtos", source = "comments")
    InfoTaskResponse toModels(Task task, List<Comment> comments);

    InfoTaskResponse toModel(Task task);

    TaskDeleteResponse toModelDelete(Task task);

    InfoTaskMini toModelInfo(Task task);

    InfoExecutorResponse toModelExecutor(Task task);

    Task toEntity(AddTaskRequest addTaskRequest, User author, User executor);

    @Mapping(target = "commentDtos", source = "comments")
    List<InfoTaskResponse> toModelList(Page<Task> tasks);
}
