package com.example.TMS.service;

import com.example.TMS.dto.request.AddTaskRequest;
import com.example.TMS.dto.request.UpdateTaskRequest;
import com.example.TMS.dto.response.InfoTaskResponse;
import com.example.TMS.dto.response.TaskDeleteResponse;
import com.example.TMS.entity.User;

import java.util.UUID;

public interface TaskService {

    InfoTaskResponse addTask(AddTaskRequest addTaskRequest, User user);

    TaskDeleteResponse deleteTaskByUuid(UUID uuid);

    InfoTaskResponse getByUuid(UUID uuid);

    InfoTaskResponse updateTask(UUID uuid, UpdateTaskRequest updateTaskRequest, User user);
}
