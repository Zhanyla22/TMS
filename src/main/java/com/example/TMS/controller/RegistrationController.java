package com.example.TMS.controller;

import com.example.TMS.dto.request.ConfirmCodeRequest;
import com.example.TMS.dto.request.RegistrationRequest;
import com.example.TMS.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationController {

    UsersService usersService;

    @Operation(summary = "регистрация по почте, отправляется код на почту,код надо написать на апи: /confirm  чтобы подтвердить свою почту)")
    @PostMapping
    public ResponseEntity<String> registration(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok().body(usersService.registration(request));
    }

    @Operation(summary = "подтверждение почты с помощью кода")
    @PostMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestBody ConfirmCodeRequest request) {
        return ResponseEntity.ok().body(usersService.confirm(request));
    }
}
