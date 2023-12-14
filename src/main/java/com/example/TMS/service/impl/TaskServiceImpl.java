package com.example.TMS.service.impl;

import com.example.TMS.dto.request.AddTaskRequest;
import com.example.TMS.dto.request.UpdateTaskRequest;
import com.example.TMS.dto.response.InfoExecutorResponse;
import com.example.TMS.dto.response.InfoTaskMini;
import com.example.TMS.dto.response.InfoTaskResponse;
import com.example.TMS.dto.response.TaskDeleteResponse;
import com.example.TMS.entity.Task;
import com.example.TMS.entity.User;
import com.example.TMS.enums.Status;
import com.example.TMS.enums.StatusTask;
import com.example.TMS.exception.common.NotAllowedException;
import com.example.TMS.exception.common.TaskNotFoundException;
import com.example.TMS.exception.common.UserNotFoundException;
import com.example.TMS.mapper.TaskMapper;
import com.example.TMS.repository.TaskRepository;
import com.example.TMS.repository.UsersRepository;
import com.example.TMS.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    UsersRepository usersRepository;
    TaskMapper taskMapper;

    @Override
    public InfoTaskResponse addTask(AddTaskRequest addTaskRequest, User user) {
        User author = usersRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new UserNotFoundException(user.getEmail(), HttpStatus.NOT_FOUND)
        );
        User executor = addTaskRequest.getExecutorId() != null ?
                usersRepository.findById(addTaskRequest.getExecutorId())
                        .orElseThrow(() -> new UserNotFoundException(addTaskRequest.getExecutorId(), HttpStatus.NOT_FOUND))
                : null;
        Task task = taskMapper.toEntity(addTaskRequest, author, executor);
        task.setStatus(Status.ACTIVE);
        task.setUuid(UUID.randomUUID());
        return taskMapper.toModel(taskRepository.save(task));
    }

    @Override
    public TaskDeleteResponse deleteTaskByUuid(UUID uuid, User user) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException(uuid, HttpStatus.NOT_FOUND));
        if(!user.getEmail().equals(task.getAuthor().getEmail())){
            throw new NotAllowedException("delete",HttpStatus.METHOD_NOT_ALLOWED);
        }
        task.setStatus(Status.DELETED);

        return taskMapper.toModelDelete(taskRepository.save(task));
    }

    @Override
    public InfoTaskResponse getByUuid(UUID uuid) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException(uuid, HttpStatus.NOT_FOUND));
        return taskMapper.toModel(task);
    }

    @Override
    public InfoTaskResponse updateTask(UUID uuid, UpdateTaskRequest updateTaskRequest, User user) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException(uuid, HttpStatus.NOT_FOUND));

        if (!user.getEmail().equals(task.getAuthor().getEmail())) {
            throw new NotAllowedException("update task", HttpStatus.METHOD_NOT_ALLOWED);
        }
            task.setTitle(updateTaskRequest.getTitle());
            task.setDescription(updateTaskRequest.getDescription());
            task.setStatusTask(updateTaskRequest.getStatusTask());
            task.setPriority(updateTaskRequest.getPriority());
        if (updateTaskRequest.getExecutorId() != null) {
            User executor = usersRepository.findById(updateTaskRequest.getExecutorId()).orElseThrow(
                    () -> new UserNotFoundException(updateTaskRequest.getExecutorId(), HttpStatus.NOT_FOUND));
            task.setExecutor(executor);
        }

        return taskMapper.toModel(taskRepository.save(task));
    }

    @Override
    public InfoTaskMini changeStatus(UUID uuid, StatusTask statusTask, User user) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException(uuid, HttpStatus.NOT_FOUND));
        if (!user.getEmail().equals(task.getAuthor().getEmail()) & !user.getEmail().equals(task.getExecutor().getEmail())) { //TODO:check
            throw new NotAllowedException("change status", HttpStatus.METHOD_NOT_ALLOWED);
        }
        task.setStatusTask(statusTask);

        return taskMapper.toModelInfo(taskRepository.save(task));
    }

    @Override
    public InfoExecutorResponse addExecutor(UUID uuid, Long executorId, User user) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException(uuid, HttpStatus.NOT_FOUND));

        if (!user.getEmail().equals(task.getAuthor().getEmail())) {
            throw new NotAllowedException("add executor", HttpStatus.METHOD_NOT_ALLOWED);
        }
        User executor = usersRepository.findById(executorId).orElseThrow(
                () -> new UserNotFoundException(executorId, HttpStatus.NOT_FOUND));
        task.setExecutor(executor);

        return taskMapper.toModelExecutor(taskRepository.save(task));
    }
}
