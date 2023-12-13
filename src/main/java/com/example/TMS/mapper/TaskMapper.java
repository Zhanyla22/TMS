package com.example.TMS.mapper;

import com.example.TMS.dto.request.AddTaskRequest;
import com.example.TMS.dto.response.InfoTaskResponse;
import com.example.TMS.entity.Task;
import com.example.TMS.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    InfoTaskResponse toModel(Task task);

    Task toEntity(AddTaskRequest addTaskRequest, User author, User executor);
}
