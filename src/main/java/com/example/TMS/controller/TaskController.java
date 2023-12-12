package com.example.TMS.controller;

import com.example.TMS.controller.base.BaseController;
import com.example.TMS.dto.ResponseDto;
import com.example.TMS.dto.request.AddTaskRequest;
import com.example.TMS.dto.request.UpdateTaskRequest;
import com.example.TMS.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController extends BaseController {

    private final TaskService taskService;

    /**
     * @param addTaskRequest title - заголовок;
     *                       description - описание;
     *                       status(enum) - статус задачи;
     *                       priority(enum) - приоритетность;
     *                       executeId - id исполнителя;
     * @param token          - токен
     * @return ResponseDto
     */
    @Operation(summary = "Добавление новой задачи")
    @PostMapping("/add")
    public ResponseEntity<ResponseDto> add(@RequestBody AddTaskRequest addTaskRequest, @RequestHeader("Authorization") String token) {
        return constructSuccessResponse(taskService.addTask(addTaskRequest, token));
    }

    /**
     * @param uuid - для поиска задачи по uuid
     * @return ResponseDto
     */
    @Operation(summary = "Удаление задачи по uuid")
    @PostMapping("/delete/{uuid}")
    public ResponseEntity<ResponseDto> delete(@PathVariable UUID uuid) {
        return constructSuccessResponse(taskService.deleteTaskByUuid(uuid));
    }

    /**
     * @param uuid- для поиска задачи по uuid
     * @return
     */
    @Operation(summary = "Просмотр задачи по uuid")
    @GetMapping("/get/{uuid}")
    public ResponseEntity<ResponseDto> getByUuid(@PathVariable UUID uuid) {
        return constructSuccessResponse(taskService.getByUuid(uuid));
    }

    /**
     * @param uuid              для поиска задачи по uuid
     * @param updateTaskRequest    title - заголовок;
     *                          *   description - описание;
     *                          *   status(enum) - статус задачи;
     *                          *   priority(enum) - приоритетность;
     *                          *   executeId - id исполнителя;
     * @param token             токен
     * @return ResponseDto
     */
    @Operation(summary = "Обновление задачи по uuid (Автор задачи может изменять:  " +
            "    title;\n" +
            "    description;\n" +
            "    priority;\n" +
            "    status;\n" +
            "    executeId(id исполнителя)) ;" +
            "исполнитель задачи может изменять : статус")
    @PutMapping("/update/{uuid}")
    private ResponseEntity<ResponseDto> updateTask(@PathVariable UUID uuid, @RequestBody UpdateTaskRequest updateTaskRequest, @RequestHeader("Authorization") String token) {
        return constructSuccessResponse(taskService.updateTask(uuid, updateTaskRequest, token));
    }
}
