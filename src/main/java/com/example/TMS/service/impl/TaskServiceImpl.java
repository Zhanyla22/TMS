package com.example.TMS.service.impl;

import com.example.TMS.dto.request.AddTaskRequest;
import com.example.TMS.dto.request.UpdateTaskRequest;
import com.example.TMS.dto.response.InfoTaskResponse;
import com.example.TMS.dto.response.TaskDeleteResponse;
import com.example.TMS.entity.Task;
import com.example.TMS.entity.Users;
import com.example.TMS.enums.Status;
import com.example.TMS.exception.base.BaseException;
import com.example.TMS.mapper.TaskMapper;
import com.example.TMS.repository.TaskRepository;
import com.example.TMS.repository.UsersRepository;
import com.example.TMS.security.jwt.JWTService;
import com.example.TMS.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UsersRepository usersRepository;

    @Override
    public InfoTaskResponse addTask(AddTaskRequest addTaskRequest, Users users) {
        Users users1 = usersRepository.findByEmail(users.getEmail()).orElseThrow(
                () -> new BaseException("пользователь с почтой" + users.getEmail() + " не найден", HttpStatus.NOT_FOUND)
        );
        Users executer = usersRepository.findById(addTaskRequest.getExecuteId()).orElseThrow(
                () -> new BaseException("пользователь с id " + addTaskRequest.getExecuteId() + " не найден", HttpStatus.NOT_FOUND)
        );
        Task task = new Task();
        task.setTitle(addTaskRequest.getTitle());
        task.setDescription(addTaskRequest.getDescription());
        task.setStatus(addTaskRequest.getStatus());
        task.setPriority(addTaskRequest.getPriority());
        task.setUuid(UUID.randomUUID());//TODO: check
        task.setAuthor(users1);
        task.setExecuter(executer);
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
    public InfoTaskResponse updateTask(UUID uuid, UpdateTaskRequest updateTaskRequest, Users users) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new BaseException("задача с uuid " + uuid + "Не найдена", HttpStatus.NOT_FOUND)
        );
        if (users.getEmail().equals(task.getAuthor().getEmail())) {
            if (updateTaskRequest.getTitle() != null) {
                task.setTitle(updateTaskRequest.getTitle());
            }
            if (updateTaskRequest.getDescription() != null) {
                task.setDescription(updateTaskRequest.getDescription());
            }
            if (updateTaskRequest.getStatus() != null) {
                task.setStatus(updateTaskRequest.getStatus());
            }
            if (updateTaskRequest.getPriority() != null) {
                task.setPriority(updateTaskRequest.getPriority());
            }
            if (updateTaskRequest.getExecuteId() != null) {
                Users users1 = usersRepository.findById(updateTaskRequest.getExecuteId()).orElseThrow(
                        () -> new BaseException("пользователь с Id " + updateTaskRequest.getExecuteId() + "Не найден", HttpStatus.NOT_FOUND)
                );
                task.setExecuter(users1);
            }
            taskRepository.save(task);
            return TaskMapper.entityToResponse(task);

        } else if (users.getEmail().equals(task.getExecuter().getEmail())) {
            if (updateTaskRequest.getStatus() != null)
                task.setStatus(updateTaskRequest.getStatus());
            taskRepository.save(task); //TODO: как можно сюда тоже добавить эксепшн, что он не может менять ничего кроме статуса
            return TaskMapper.entityToResponse(task);
        } else
            throw new BaseException("Вы не можете менять не свои задачи или не предназначенные вам", HttpStatus.NOT_ACCEPTABLE);
    }
}
