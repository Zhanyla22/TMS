package com.example.TMS.service;

import com.example.TMS.dto.request.AddTaskRequest;
import com.example.TMS.dto.request.UpdateTaskRequest;
import com.example.TMS.dto.response.InfoTaskResponse;
import com.example.TMS.dto.response.TaskDeleteResponse;
import com.example.TMS.entity.Users;

import java.util.UUID;

public interface TaskService {

    InfoTaskResponse addTask(AddTaskRequest addTaskRequest, Users users);

    TaskDeleteResponse deleteTaskByUuid(UUID uuid);

    InfoTaskResponse getByUuid(UUID uuid);

    InfoTaskResponse updateTask(UUID uuid,UpdateTaskRequest updateTaskRequest,Users users);
}
