package com.example.TMS.controller;

import com.example.TMS.dto.request.AuthRequest;
import com.example.TMS.dto.response.AuthResponse;
import com.example.TMS.entity.User;
import com.example.TMS.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    /**
     * @param authRequest email - почта(есть валидация)
     *                    password - пароль
     * @return ResponseDto
     */
    @Operation(summary = "авторизация по почте и пароли, открытый API")
    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok().body(authService.auth(authRequest));
    }

    @Operation(summary = "рефреш токен")
    @GetMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(authService.refresh(user));
    }
}
