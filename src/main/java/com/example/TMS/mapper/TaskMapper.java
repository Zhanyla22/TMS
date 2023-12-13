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
                .uuid(task.getUuid())
                .id(task.getId())
                .priority(task.getPriority())
                .statusTask(task.getStatusTask())
                .status(task.getStatus())
                .author(UsersMapper.entityToResponse(task.getAuthor()))
                .executor(UsersMapper.entityToResponse(task.getExecutor()))
                .createdDate(task.getCreatedDate())
                .build();
    }

    public static TaskDeleteResponse entityToDeleteResponse(Task task){
        return TaskDeleteResponse.builder()
                .uuid(task.getUuid())
                .status(task.getStatus())
                .build();
    }
}
