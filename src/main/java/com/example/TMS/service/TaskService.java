package com.example.TMS.service;

import com.example.TMS.dto.request.TaskAddRequest;
import com.example.TMS.dto.request.TaskUpdateRequest;
import com.example.TMS.dto.response.ExecutorInfoResponse;
import com.example.TMS.dto.response.TaskInfoMiniResponse;
import com.example.TMS.dto.response.InfoTaskResponse;
import com.example.TMS.dto.response.TaskDeleteResponse;
import com.example.TMS.entity.User;
import com.example.TMS.enums.StatusTask;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    InfoTaskResponse addTask(TaskAddRequest taskAddRequest, User user);

    TaskDeleteResponse deleteTaskByUuid(UUID uuid, User user);

    InfoTaskResponse getByUuid(UUID uuid);

    InfoTaskResponse updateTask(UUID uuid, TaskUpdateRequest updateTaskRequest, User user);

    TaskInfoMiniResponse changeStatus(UUID uuid, StatusTask statusTask, User user);

    ExecutorInfoResponse addExecutor(UUID uuid, Long executorId, User user);

    List<InfoTaskResponse> getTasksByCurrentUser(User author, PageRequest pageRequest);

    List<InfoTaskResponse> getTasksByExecutor(User executor, PageRequest pageRequest);

    List<InfoTaskResponse> getAllFilter(StatusTask statusTask, Long authorId, Long executorId, PageRequest pageRequest);

    List<InfoTaskResponse> getAllOrderByCreatedDate(PageRequest pageRequest);
}
