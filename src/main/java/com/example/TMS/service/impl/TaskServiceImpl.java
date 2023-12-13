package com.example.TMS.service.impl;

import com.example.TMS.dto.request.AddTaskRequest;
import com.example.TMS.dto.request.UpdateTaskRequest;
import com.example.TMS.dto.response.InfoTaskResponse;
import com.example.TMS.dto.response.TaskDeleteResponse;
import com.example.TMS.entity.Task;
import com.example.TMS.entity.User;
import com.example.TMS.enums.Status;
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

    @Override
    public InfoTaskResponse addTask(AddTaskRequest addTaskRequest, User user) {
        User author = usersRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new BaseException("пользователь с почтой" + user.getEmail() + " не найден", HttpStatus.NOT_FOUND)
        );
        User executor = usersRepository.findById(addTaskRequest.getExecutorId()).orElseThrow(
                () -> new BaseException("пользователь с id " + addTaskRequest.getExecutorId() + " не найден", HttpStatus.NOT_FOUND)
        );
        Task task = new Task();
        task.setTitle(addTaskRequest.getTitle());
        task.setDescription(addTaskRequest.getDescription());
        task.setStatusTask(addTaskRequest.getStatusTask());
        task.setPriority(addTaskRequest.getPriority());
        task.setUuid(UUID.randomUUID());//TODO: check
        task.setAuthor(author);
        task.setExecutor(executor);
        task.setStatus(Status.ACTIVE);
        taskRepository.save(task);
        return TaskMapper.entityToResponse(task);
    }

    @Override
    public TaskDeleteResponse deleteTaskByUuid(UUID uuid) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new BaseException("задача с uuid " + uuid + "не найдена", HttpStatus.NOT_FOUND)
        );
        task.setStatus(Status.DELETED);
        taskRepository.save(task);

        return TaskMapper.entityToDeleteResponse(task);
    }

    @Override
    public InfoTaskResponse getByUuid(UUID uuid) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new BaseException("задача с uuid " + "не найдена", HttpStatus.NOT_FOUND)
        );
        return TaskMapper.entityToResponse(task);
    }

    @Override
    public InfoTaskResponse updateTask(UUID uuid, UpdateTaskRequest updateTaskRequest, User user) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new BaseException("задача с uuid " + uuid + "Не найдена", HttpStatus.NOT_FOUND)
        );
        if (user.getEmail().equals(task.getAuthor().getEmail())) {
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
            task.setStatus(Status.UPDATED);
            if (updateTaskRequest.getExecutorId() != null) {
                User executor = usersRepository.findById(updateTaskRequest.getExecutorId()).orElseThrow(
                        () -> new BaseException("пользователь с Id " + updateTaskRequest.getExecutorId() + "Не найден", HttpStatus.NOT_FOUND)
                );
                task.setExecutor(executor);
            }
            taskRepository.save(task);
            return TaskMapper.entityToResponse(task);

        } else if (user.getEmail().equals(task.getExecutor().getEmail())) {
            if (updateTaskRequest.getStatusTask() != null)
                task.setStatusTask(updateTaskRequest.getStatusTask());
            taskRepository.save(task); //TODO: как можно сюда тоже добавить эксепшн, что он не может менять ничего кроме статуса
            return TaskMapper.entityToResponse(task);
        } else
            throw new BaseException("Вы не можете менять не свои задачи или не предназначенные вам", HttpStatus.NOT_ACCEPTABLE);
    }
}
