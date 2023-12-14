package com.example.TMS.controller;

import com.example.TMS.dto.request.AddTaskRequest;
import com.example.TMS.dto.request.UpdateTaskRequest;
import com.example.TMS.dto.response.InfoExecutorResponse;
import com.example.TMS.dto.response.InfoTaskMini;
import com.example.TMS.dto.response.InfoTaskResponse;
import com.example.TMS.dto.response.TaskDeleteResponse;
import com.example.TMS.entity.User;
import com.example.TMS.enums.StatusTask;
import com.example.TMS.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {

    TaskService taskService;

    @Operation(summary = "Добавление новой задачи")
    @PostMapping("/add")
    public ResponseEntity<InfoTaskResponse> add(@RequestBody AddTaskRequest addTaskRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(taskService.addTask(addTaskRequest, user));
    }

    @Operation(summary = "Удаление задачи по uuid")
    @PutMapping("/delete/{uuid}")
    public ResponseEntity<TaskDeleteResponse> delete(@PathVariable UUID uuid, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(taskService.deleteTaskByUuid(uuid, user));
    }

    @Operation(summary = "Просмотр задачи по uuid")
    @GetMapping("/get/{uuid}")
    public ResponseEntity<InfoTaskResponse> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok().body(taskService.getByUuid(uuid));
    }

    @Operation(summary = "Обновление задачи по uuid (только автор задачи может изменять:  " +
            "    title;\n" +
            "    description;\n" +
            "    priority;\n" +
            "    status;\n" +
            "    executeId(id исполнителя)) ;")
    @PutMapping("/update/{uuid}")
    public ResponseEntity<InfoTaskResponse> updateTask(@PathVariable UUID uuid,
                                                       @RequestBody UpdateTaskRequest updateTaskRequest,
                                                       @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(taskService.updateTask(uuid, updateTaskRequest, user));
    }

    @Operation(summary = "менять статус задачи : менять могут только автор задачи либо исполнитель задачи")
    @PutMapping("/change-status/{uuid}")
    public ResponseEntity<InfoTaskMini> changeStatus(@PathVariable UUID uuid,
                                                     @RequestParam StatusTask statusTask,
                                                     @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(taskService.changeStatus(uuid, statusTask, user));
    }

    @Operation(summary = "добавление исполнителя задачи, добавлять может только автор задачи")
    @PutMapping("/add-executor/{uuid}")
    public ResponseEntity<InfoExecutorResponse> addExecutor(@PathVariable UUID uuid,
                                                            @RequestParam @NotNull Long executorId,
                                                            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(taskService.addExecutor(uuid, executorId, user));
    }


    @Operation(summary = "Получение всех задач автора которые он создал с пагинацией(sortBy: сортировка по поле;" +
            "pageSize - количество записей на 1 страницу; pageNumber - начинается с 0, страница)")
    @GetMapping("/get-all-active-author-task-by-current-user")
    public ResponseEntity<List<InfoTaskResponse>> getTaskByCurrentUser(@AuthenticationPrincipal User user,
                                                                       @RequestParam("sortBy") Optional<String> sortBy,
                                                                       @RequestParam("pageSize") Optional<Integer> pageSize,
                                                                       @RequestParam("pageNumber") Optional<Integer> pageNumber) {
        return ResponseEntity.ok().body(taskService.getTasksByCurrentUser(user, PageRequest.of(pageNumber.orElse(0), pageSize.orElse(1000), Sort.by(sortBy.orElse("id")))));
    }

    @Operation(summary = "Получение всех задач на исполнение пользователя с пагинацией (sortBy: сортировка по поле;\" +\n" +
            "            \"pageSize - количество записей на 1 страницу; pageNumber - начинается с 0, страница)")
    @GetMapping("/get-all-active-task-to-do-by-current-user")
    public ResponseEntity<List<InfoTaskResponse>> getTasksTodoByCurrentUser(@AuthenticationPrincipal User user,
                                                                            @RequestParam("sortBy") Optional<String> sortBy,
                                                                            @RequestParam("pageSize") Optional<Integer> pageSize,
                                                                            @RequestParam("pageNumber") Optional<Integer> pageNumber) {
        return ResponseEntity.ok().body(taskService.getTasksByExecutor(user, PageRequest.of(pageNumber.orElse(0), pageSize.orElse(1000), Sort.by(sortBy.orElse("id")))));
    }

    @Operation(summary = "Фильтрация с пагинацией по статусу задачи(можно оставлять пустой), по автору задачи(можно оставлять пустой),по исполнителю(можно оставлять пустой)" +
            "; (sortBy: сортировка по поле;\" +\n" +
            "            \"pageSize - количество записей на 1 страницу; pageNumber - начинается с 0, страница)")
    @GetMapping("/filter")
    public ResponseEntity<List<InfoTaskResponse>> filter(@RequestParam(required = false) StatusTask statusTask,
                                                         @RequestParam(required = false) Long authorId,
                                                         @RequestParam(required = false) Long executorId,
                                                         @RequestParam("sortBy") Optional<String> sortBy,
                                                         @RequestParam("pageSize") Optional<Integer> pageSize,
                                                         @RequestParam("pageNumber") Optional<Integer> pageNumber) {
        if (statusTask != null || authorId != null || executorId != null) {
            return ResponseEntity.ok().body(taskService.getAllFilter(statusTask, authorId, executorId, PageRequest.of(pageNumber.orElse(0), pageSize.orElse(1000), Sort.by(sortBy.orElse("id")))));
        } else {
            return ResponseEntity.ok().body(taskService.getAllOrderByCreatedDate(PageRequest.of(pageNumber.orElse(0), pageSize.orElse(1000), Sort.by(sortBy.orElse("id")))));
        }
    }


}

