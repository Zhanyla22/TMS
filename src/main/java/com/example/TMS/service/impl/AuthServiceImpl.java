package com.example.TMS.service.impl;

import com.example.TMS.dto.request.AuthRequest;
import com.example.TMS.dto.response.AuthResponse;
import com.example.TMS.entity.User;
import com.example.TMS.enums.Status;
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

    /**
     * Логин с
     *
     * @param authRequest принимает пароль и почту от фронта, передаются на метод authenticate
     *                    где происходит авторизация, при успешном кейсе генерируется аксесс токен и рефреш токен
     * @return AuthResponse - содержит аксесс токенб рейреш токен и их время жизни
     */
    @Override
    public AuthResponse auth(AuthRequest authRequest) {
        User user = usersRepository.findByEmail(authRequest.getEmail()).orElseThrow(
                () -> new UserNotFoundException("user with email " + authRequest.getEmail() + "not found", HttpStatus.NOT_FOUND));
        if (!user.getStatus().equals(Status.ACTIVE)) {
            throw new BaseException("Your email wasn't confirmed", HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
            return jwtService.generateToken((User) authenticate.getPrincipal());
        } catch (Exception e) {
            throw new AuthenticationException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * перегенерация токенов
     *
     * @param user
     * @return AuthResponse
     */
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
