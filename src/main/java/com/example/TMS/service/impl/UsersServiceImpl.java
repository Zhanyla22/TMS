package com.example.TMS.service.impl;

import com.example.TMS.dto.request.AuthRequest;
import com.example.TMS.dto.response.AuthResponse;
import com.example.TMS.entity.User;
import com.example.TMS.exception.base.BaseException;
import com.example.TMS.security.jwt.JWTService;
import com.example.TMS.service.UsersService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersServiceImpl implements UsersService {

    AuthenticationManager authenticationManager;
    JWTService jwtService;

    @Override
    public AuthResponse auth(AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
            return jwtService.generateToken((User) authenticate.getPrincipal());
        } catch (Exception e) {
            throw new BaseException("пароль или логин неправильный", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
