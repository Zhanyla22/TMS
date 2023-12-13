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
import com.example.TMS.exception.NotAllowedException;
import com.example.TMS.exception.TaskNotFoundException;
import com.example.TMS.exception.UserNotFoundException;
import com.example.TMS.exception.base.BaseException;
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
                () -> new UserNotFoundException("пользователь с почтой" + user.getEmail() + " не найден", HttpStatus.NOT_FOUND) //TODO: надо ли проверять так пользовател по почте?
        );
        if (addTaskRequest.getExecutorId() != null) {
            User executor = usersRepository.findById(addTaskRequest.getExecutorId()).orElseThrow(
                    () -> new UserNotFoundException("пользователь с id " + addTaskRequest.getExecutorId() + " не найден", HttpStatus.NOT_FOUND)
            );

            Task task = taskMapper.toEntity(addTaskRequest, author, executor);
            task.setStatus(Status.ACTIVE);
            task.setUuid(UUID.randomUUID());
            taskRepository.save(task);
            return taskMapper.toModel(task);
        }
        Task task = taskMapper.toEntity(addTaskRequest, author, null);
        task.setStatus(Status.ACTIVE);
        task.setUuid(UUID.randomUUID());
        taskRepository.save(task);
        return taskMapper.toModel(task);


    }

    @Override
    public TaskDeleteResponse deleteTaskByUuid(UUID uuid) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException("задача с uuid " + uuid + "не найдена", HttpStatus.NOT_FOUND)
        );
        task.setStatus(Status.DELETED);
        taskRepository.save(task);

        return taskMapper.toModelDelete(task);
    }

    @Override
    public InfoTaskResponse getByUuid(UUID uuid) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException("задача с uuid " + uuid+"не найдена", HttpStatus.NOT_FOUND)
        );
        return taskMapper.toModel(task);
    }

    @Override
    public InfoTaskResponse updateTask(UUID uuid, UpdateTaskRequest updateTaskRequest, User user) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException("задача с uuid " + uuid + "Не найдена", HttpStatus.NOT_FOUND)
        );
        //TODO: MAPSTRUCt
        if (!user.getEmail().equals(task.getAuthor().getEmail())) {
            throw new NotAllowedException("Вы не можете менять не свои задачи или не предназначенные вам", HttpStatus.NOT_ACCEPTABLE);
        }
        if (updateTaskRequest.getTitle() != null) {
            task.setTitle(updateTaskRequest.getTitle());
        }
        if (updateTaskRequest.getDescription() != null) {
            task.setDescription(updateTaskRequest.getDescription());
        }
        if (updateTaskRequest.getStatusTask() != null) {
            task.setStatusTask(updateTaskRequest.getStatusTask());
        }
        if (updateTaskRequest.getPriority() != null) {
            task.setPriority(updateTaskRequest.getPriority());
        }
        if (updateTaskRequest.getExecutorId() != null) {
            User executor = usersRepository.findById(updateTaskRequest.getExecutorId()).orElseThrow(
                    () -> new UserNotFoundException("пользователь с Id " + updateTaskRequest.getExecutorId() + "Не найден", HttpStatus.NOT_FOUND)
            );
            task.setExecutor(executor);
        }
        task.setStatus(Status.UPDATED);
        taskRepository.save(task);
        return taskMapper.toModel(task);
    }

    @Override
    public InfoTaskMini changeStatus(UUID uuid, StatusTask statusTask, User user) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException("Задача с uuid " + uuid + "не найдена", HttpStatus.NOT_FOUND)
        );
        if (!user.getEmail().equals(task.getAuthor().getEmail()) & !user.getEmail().equals(task.getExecutor().getEmail())) { //TODO:check
            throw new NotAllowedException("Вы не являетесь автором либо исполнителем этой задачи, соответвенно не можете менять статус", HttpStatus.NOT_ACCEPTABLE);
        } //TODO: EXCEPTION add
        task.setStatusTask(statusTask);
        task.setStatus(Status.UPDATED);
        taskRepository.save(task);
        return taskMapper.toModelInfo(task);
    }

    @Override
    public InfoExecutorResponse addExecutor(UUID uuid, Long executorId, User user) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException("задача с uuid" + uuid + "не найдена", HttpStatus.NOT_FOUND)
        );
        if (!user.getEmail().equals(task.getAuthor().getEmail())) {
            throw new NotAllowedException("вы не являетесь автором задачи, не можете добавлять исполнителя", HttpStatus.NOT_FOUND);
        }
        User executor = usersRepository.findById(executorId).orElseThrow(
                () -> new UserNotFoundException("пользователь с Id " + executorId + "не найден", HttpStatus.NOT_FOUND)
        );
        task.setExecutor(executor);
        task.setStatus(Status.UPDATED);
        taskRepository.save(task);
        return taskMapper.toModelExecutor(task);
    }
}
