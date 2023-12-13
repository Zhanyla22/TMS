package com.example.TMS.controller;

import com.example.TMS.controller.base.BaseController;
import com.example.TMS.dto.ResponseDto;
import com.example.TMS.dto.request.AuthRequest;
import com.example.TMS.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController extends BaseController {

    UsersService usersService;

    /**
     * @param authRequest email - почта(есть валидация)
     *                    password - пароль
     * @return ResponseDto
     */
    @Operation(summary = "авторизация по почте и пароли, открытый API")
    @PostMapping("/auth")
    public ResponseEntity<ResponseDto> auth(@RequestBody AuthRequest authRequest){
        return constructSuccessResponse(usersService.auth(authRequest));
    }
}
