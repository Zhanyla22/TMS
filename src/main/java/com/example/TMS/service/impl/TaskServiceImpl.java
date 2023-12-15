package com.example.TMS.service.impl;

import com.example.TMS.dto.request.AddTaskRequest;
import com.example.TMS.dto.request.UpdateTaskRequest;
import com.example.TMS.dto.response.InfoExecutorResponse;
import com.example.TMS.dto.response.InfoTaskMini;
import com.example.TMS.dto.response.InfoTaskResponse;
import com.example.TMS.dto.response.TaskDeleteResponse;
import com.example.TMS.entity.Comment;
import com.example.TMS.entity.Task;
import com.example.TMS.entity.User;
import com.example.TMS.enums.Status;
import com.example.TMS.enums.StatusTask;
import com.example.TMS.exception.common.NotAllowedException;
import com.example.TMS.exception.common.TaskNotFoundException;
import com.example.TMS.exception.common.UserNotFoundException;
import com.example.TMS.mapper.CommentMapper;
import com.example.TMS.mapper.TaskMapper;
import com.example.TMS.repository.CommentRepository;
import com.example.TMS.repository.TaskRepository;
import com.example.TMS.repository.UsersRepository;
import com.example.TMS.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    UsersRepository usersRepository;
    TaskMapper taskMapper;
    CommentRepository commentRepository;
    CommentMapper commentMapper;

    /**
     * Добавление новой задачи
     * @param addTaskRequest - все поля кроме title могут быть пустыми
     * @param user - пользователь который сейчас logged-in
     * @return InfoTaskResponse - информация о новой задаче которого только что добавили
     */
    @Override
    public InfoTaskResponse addTask(AddTaskRequest addTaskRequest, User user) {
        User author = usersRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new UserNotFoundException(user.getEmail(), HttpStatus.NOT_FOUND)
        );
        // id исполнителя проверяется на ноль чтобы избежать ошибки Null при поиске пользователя в БД,
        // так как при добавлении задачи поле executor может быть пустым
        User executor = addTaskRequest.getExecutorId() != null ?
                usersRepository.findById(addTaskRequest.getExecutorId())
                        .orElseThrow(() -> new UserNotFoundException(addTaskRequest.getExecutorId(), HttpStatus.NOT_FOUND))
                : null;
        Task task = taskMapper.toEntity(addTaskRequest, author, executor);
        task.setStatus(Status.ACTIVE);
        task.setUuid(UUID.randomUUID());
        return taskMapper.toModel(taskRepository.save(task));
    }

    /**
     * Удаление задачи по uuid(более безопасный чем по id)
     * При удалении меняется только статус на deleted
     * @param uuid
     * @param user
     * @return
     */
    @Override
    public TaskDeleteResponse deleteTaskByUuid(UUID uuid, User user) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException(uuid, HttpStatus.NOT_FOUND));
        //Задачу может удалить только автор
        if (!user.getEmail().equals(task.getAuthor().getEmail())) {
            throw new NotAllowedException("delete", HttpStatus.METHOD_NOT_ALLOWED);
        }
        task.setStatus(Status.DELETED);

        return taskMapper.toModelDelete(taskRepository.save(task));
    }

    /**
     * Обновление задачи
     * @param uuid
     * @param updateTaskRequest
     * @param user
     * @return
     */
    @Override
    public InfoTaskResponse updateTask(UUID uuid, UpdateTaskRequest updateTaskRequest, User user) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException(uuid, HttpStatus.NOT_FOUND));

        //Задачу может обновить только автор
        if (!user.getEmail().equals(task.getAuthor().getEmail())) {
            throw new NotAllowedException("update task", HttpStatus.METHOD_NOT_ALLOWED);
        }
        task.setTitle(updateTaskRequest.getTitle());
        task.setDescription(updateTaskRequest.getDescription());
        task.setStatusTask(updateTaskRequest.getStatusTask());
        task.setPriority(updateTaskRequest.getPriority());
        // id исполнителя проверяется на ноль чтобы избежать ошибки Null при поиске пользователя в БД,
        // так как при обновлении задачи поле executor может быть пустым
        if (updateTaskRequest.getExecutorId() != null) {
            User executor = usersRepository.findById(updateTaskRequest.getExecutorId()).orElseThrow(
                    () -> new UserNotFoundException(updateTaskRequest.getExecutorId(), HttpStatus.NOT_FOUND));
            task.setExecutor(executor);
        }

        return taskMapper.toModel(taskRepository.save(task));
    }

    /**
     * Поменять статус задачи
     * @param uuid
     * @param statusTask
     * @param user
     * @return
     */
    @Override
    public InfoTaskMini changeStatus(UUID uuid, StatusTask statusTask, User user) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException(uuid, HttpStatus.NOT_FOUND));
        //Статус задачи может менять сам автор либо исполнитель
        if (!user.getEmail().equals(task.getAuthor().getEmail()) & !user.getEmail().equals(task.getExecutor().getEmail())) { //TODO:check
            throw new NotAllowedException("change status", HttpStatus.METHOD_NOT_ALLOWED);
        }
        task.setStatusTask(statusTask);

        return taskMapper.toModelInfo(taskRepository.save(task));
    }

    /**
     * добавление исполнителя задачи
     * @param uuid
     * @param executorId
     * @param user
     * @return
     */
    @Override
    public InfoExecutorResponse addExecutor(UUID uuid, Long executorId, User user) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException(uuid, HttpStatus.NOT_FOUND));

        //исполнителя может добавлять только автор
        if (!user.getEmail().equals(task.getAuthor().getEmail())) {
            throw new NotAllowedException("add executor", HttpStatus.METHOD_NOT_ALLOWED);
        }
        User executor = usersRepository.findById(executorId).orElseThrow(
                () -> new UserNotFoundException(executorId, HttpStatus.NOT_FOUND));
        task.setExecutor(executor);

        return taskMapper.toModelExecutor(taskRepository.save(task));
    }

    /**
     * Все созданные задачи автора
     * @param user
     * @param pageRequest
     * @return
     */
    @Override
    public List<InfoTaskResponse> getTasksByCurrentUser(User user, PageRequest pageRequest) {
        Page<Task> tasks = taskRepository.findAllByAuthorAndStatus(user, Status.ACTIVE, pageRequest);
        List<InfoTaskResponse> taskResponses = taskMapper.toModelList(tasks);
        for(InfoTaskResponse infoTaskResponse : taskResponses){
            List<Comment> commentsForTask = commentRepository.findAllByTaskUuidAndStatus(infoTaskResponse.getUuid(), Status.ACTIVE);
            infoTaskResponse.setCommentDtos(commentMapper.mapToCommentDtos(commentsForTask));
        }
        return taskResponses;
    }

    /**
     * Все задачи которые должен выполнить пользователь
     * @param executor
     * @param pageRequest
     * @return
     */
    @Override
    public List<InfoTaskResponse> getTasksByExecutor(User executor, PageRequest pageRequest) {
        Page<Task> tasks = taskRepository.findAllByExecutorAndStatus(executor, Status.ACTIVE, pageRequest);
        List<InfoTaskResponse> taskResponses = taskMapper.toModelList(tasks);
        for(InfoTaskResponse infoTaskResponse : taskResponses){
            List<Comment> commentsForTask = commentRepository.findAllByTaskUuidAndStatus(infoTaskResponse.getUuid(), Status.ACTIVE);
            infoTaskResponse.setCommentDtos(commentMapper.mapToCommentDtos(commentsForTask));
        }
        return taskResponses;
    }

    /**
     * Получение задачи по uuid с комментариями
     * @param uuid
     * @return
     */
    @Override
    public InfoTaskResponse getByUuid(UUID uuid) {
        Task task = taskRepository.findTaskByUuid(uuid).orElseThrow(
                () -> new TaskNotFoundException(uuid, HttpStatus.NOT_FOUND));
        List<Comment> comment = commentRepository.findAllByTaskUuidAndStatus(task.getUuid(), Status.ACTIVE);
        return taskMapper.toModels(task, comment);
    }


    /**
     * Фильтрация активных задач по статусу, автору, исполнителю с пагинацией
     * @param statusTask
     * @param authorId
     * @param executorId
     * @param pageRequest
     * @return
     */
    @Override
    public List<InfoTaskResponse> getAllFilter(StatusTask statusTask,Long authorId, Long executorId,PageRequest pageRequest) {
        Page<Task> tasks = taskRepository.findAllByStatusAndStatusTaskAuthorIdOrExecutorId(Status.ACTIVE,statusTask,authorId,executorId,pageRequest);
        return taskMapper.toModelList(tasks);
    }

    /**
     * Получение всех задач отфильтрован по дате создания
     * @param pageRequest
     * @return
     */
    @Override
    public List<InfoTaskResponse> getAllOrderByCreatedDate(PageRequest pageRequest) {
        Page<Task> tasks = taskRepository.findAllByOrderByCreatedDateDesc(pageRequest);
        return taskMapper.toModelList(tasks);
    }
}
