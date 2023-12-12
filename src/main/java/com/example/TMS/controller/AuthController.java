package com.example.TMS.controller;

import com.example.TMS.controller.base.BaseController;
import com.example.TMS.dto.ResponseDto;
import com.example.TMS.dto.request.AuthRequest;
import com.example.TMS.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController extends BaseController {

    private final UsersService usersService;

    @PostMapping("/auth")
    public ResponseEntity<ResponseDto> auth(@RequestBody AuthRequest authRequest){
        return constructSuccessResponse(usersService.auth(authRequest));
    }
}
