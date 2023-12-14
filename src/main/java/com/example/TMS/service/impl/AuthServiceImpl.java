package com.example.TMS.service.impl;

import com.example.TMS.dto.request.AuthRequest;
import com.example.TMS.dto.response.AuthResponse;
import com.example.TMS.entity.User;
import com.example.TMS.exception.BaseException;
import com.example.TMS.exception.common.AuthenticationException;
import com.example.TMS.exception.common.UserNotFoundException;
import com.example.TMS.repository.UsersRepository;
import com.example.TMS.security.jwt.JWTService;
import com.example.TMS.service.AuthService;
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
public class AuthServiceImpl implements AuthService {

    AuthenticationManager authenticationManager;
    JWTService jwtService;
    UsersRepository usersRepository;

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
            throw new AuthenticationException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public AuthResponse refresh(User user) {
        User user1 = usersRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new UserNotFoundException(user.getEmail(), HttpStatus.NOT_FOUND));

        try {
            return jwtService.generateToken(user1);
        } catch (Exception e) {
            throw new BaseException("Ошибка при генерации токена", HttpStatus.NOT_FOUND);
        }
    }
}
