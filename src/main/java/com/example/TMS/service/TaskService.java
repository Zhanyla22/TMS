package com.example.TMS.service;

import com.example.TMS.dto.request.AddTaskRequest;
import com.example.TMS.dto.request.UpdateTaskRequest;
import com.example.TMS.dto.response.InfoExecutorResponse;
import com.example.TMS.dto.response.InfoTaskMini;
import com.example.TMS.dto.response.InfoTaskResponse;
import com.example.TMS.dto.response.TaskDeleteResponse;
import com.example.TMS.entity.User;
import com.example.TMS.enums.StatusTask;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    InfoTaskResponse addTask(AddTaskRequest addTaskRequest, User user);

    TaskDeleteResponse deleteTaskByUuid(UUID uuid, User user);

    InfoTaskResponse getByUuid(UUID uuid);

    InfoTaskResponse updateTask(UUID uuid, UpdateTaskRequest updateTaskRequest, User user);

    InfoTaskMini changeStatus(UUID uuid, StatusTask statusTask, User user);

    InfoExecutorResponse addExecutor(UUID uuid, Long executorId, User user);

    List<InfoTaskResponse> getTasksByCurrentUser(User author, PageRequest pageRequest);

    List<InfoTaskResponse> getTasksByExecutor(User executor, PageRequest pageRequest);

    List<InfoTaskResponse> getAllFilter(StatusTask statusTask,Long authorId,Long executorId,PageRequest pageRequest);
}
