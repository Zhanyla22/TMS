package com.example.TMS.controller;

import com.example.TMS.dto.request.CommentAddRequest;
import com.example.TMS.dto.response.CommentAddResponse;
import com.example.TMS.entity.User;
import com.example.TMS.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {

    CommentService commentService;

    @Operation(summary = "Добавление комментария к задаче")
    @PostMapping("/add/{taskUuid}")
    public ResponseEntity<CommentAddResponse> add(@RequestBody CommentAddRequest commentAddRequest, @PathVariable UUID taskUuid, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(commentService.addComment(commentAddRequest, taskUuid, user));
    }
}
