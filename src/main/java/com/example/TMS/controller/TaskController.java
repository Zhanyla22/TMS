package com.example.TMS.controller;

import com.example.TMS.controller.base.BaseController;
import com.example.TMS.dto.ResponseDto;
import com.example.TMS.dto.request.AddTaskRequest;
import com.example.TMS.dto.request.UpdateTaskRequest;
import com.example.TMS.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController extends BaseController {

    private final TaskService taskService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> add(@RequestBody AddTaskRequest addTaskRequest, @RequestHeader("Authorization") String token) {
        return constructSuccessResponse(taskService.addTask(addTaskRequest, token));
    }

    @PostMapping("/delete/{uuid}")
    public ResponseEntity<ResponseDto> delete(@PathVariable UUID uuid){
        return constructSuccessResponse(taskService.deleteTaskByUuid(uuid));
    }

    @GetMapping("/get/{uuid}")
    public ResponseEntity<ResponseDto> getByUuid(@PathVariable UUID uuid){
        return constructSuccessResponse(taskService.getByUuid(uuid));
    }

    @PutMapping("/update/{uuid}")
    private ResponseEntity<ResponseDto> updateTask(@PathVariable UUID uuid,
                                                   @RequestBody UpdateTaskRequest updateTaskRequest,
                                                   @RequestHeader("Authorization") String token){
        return constructSuccessResponse(taskService.updateTask(uuid,updateTaskRequest,token));
    }
}
