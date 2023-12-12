package com.example.TMS.mapper;

import com.example.TMS.dto.response.InfoTaskResponse;
import com.example.TMS.dto.response.TaskDeleteResponse;
import com.example.TMS.entity.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    public static InfoTaskResponse entityToResponse(Task task) {
        return InfoTaskResponse.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .uuid(task.getUuid().toString())
                .id(task.getId())
                .priority(task.getPriority())
                .status(task.getStatus())
                .author(UsersMapper.entityToResponse(task.getAuthor()))
                .execute(UsersMapper.entityToResponse(task.getExecuter()))
                .createdDate(task.getCreatedDate())
                .build();
    }

    public static TaskDeleteResponse entityToDeleteResponse(Task task){
        return TaskDeleteResponse.builder()
                .uuid(task.getUuid().toString())
                .status(task.getStatus())
                .build();
    }
}
