package com.example.TMS.service.impl;

import com.example.TMS.dto.request.AuthRequest;
import com.example.TMS.dto.response.AuthResponse;
import com.example.TMS.entity.User;
import com.example.TMS.exception.UserNotFoundException;
import com.example.TMS.exception.base.BaseException;
import com.example.TMS.repository.UsersRepository;
import com.example.TMS.security.jwt.JWTService;
import com.example.TMS.service.UsersService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersServiceImpl implements UsersService, UserDetailsService {

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
        } catch (AuthenticationException e) {
            throw new BaseException("пароль или логин неправильный", HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            throw new BaseException("что то пошло не так", HttpStatus.CONFLICT);
        }
    }

    @Override
    public AuthResponse refresh(User user) {
        User user1 = usersRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new UserNotFoundException("пользователь с email " + user.getEmail() + " не найден", HttpStatus.NOT_FOUND)
        );
        try {
            return jwtService.generateToken(user1);
        } catch (Exception e) {
            throw new BaseException("Ошибка при генерации токена", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return usersRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("пользователь с почтой :" + email + "не найден", HttpStatus.NOT_FOUND)
        );
    }
}
