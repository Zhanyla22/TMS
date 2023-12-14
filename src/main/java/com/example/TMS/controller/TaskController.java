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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {

    TaskService taskService;

    /**
     * @param addTaskRequest title - заголовок;
     *                       description - описание;
     *                       status(enum) - статус задачи;
     *                       priority(enum) - приоритетность;
     *                       executeId - id исполнителя;
     * @return ResponseDto
     */
    @Operation(summary = "Добавление новой задачи")
    @PostMapping("/add")
    public ResponseEntity<InfoTaskResponse> add(@RequestBody AddTaskRequest addTaskRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(taskService.addTask(addTaskRequest, user));
    }

    /**
     * @param uuid - для поиска задачи по uuid
     * @return ResponseDto
     */
    @Operation(summary = "Удаление задачи по uuid")
    @PostMapping("/delete/{uuid}")
    public ResponseEntity<TaskDeleteResponse> delete(@PathVariable UUID uuid, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(taskService.deleteTaskByUuid(uuid, user));
    }

    /**
     * @param uuid- для поиска задачи по uuid
     * @return
     */
    @Operation(summary = "Просмотр задачи по uuid")
    @GetMapping("/get/{uuid}")
    public ResponseEntity<InfoTaskResponse> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok().body(taskService.getByUuid(uuid));
    }

    /**
     * @param uuid              для поиска задачи по uuid
     * @param updateTaskRequest title - заголовок;
     *                          *   description - описание;
     *                          *   status(enum) - статус задачи;
     *                          *   priority(enum) - приоритетность;
     *                          *   executeId - id исполнителя;
     * @return ResponseDto
     */
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

    @PutMapping("/change-status/{uuid}")
    public ResponseEntity<InfoTaskMini> changeStatus(@PathVariable UUID uuid,
                                                     @RequestParam StatusTask statusTask,
                                                     @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(taskService.changeStatus(uuid, statusTask, user));
    }

    @PutMapping("/add-executor/{uuid}")
    public ResponseEntity<InfoExecutorResponse> addExecutor(@PathVariable UUID uuid,
                                                            @RequestParam @NotNull Long executorId,
                                                            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(taskService.addExecutor(uuid, executorId, user));
    }
}
