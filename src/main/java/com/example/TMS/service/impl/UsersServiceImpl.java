package com.example.TMS.service.impl;

import com.example.TMS.exception.common.UserNotFoundException;
import com.example.TMS.repository.UsersRepository;
import com.example.TMS.service.UsersService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersServiceImpl implements UsersService, UserDetailsService {

    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return usersRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email, HttpStatus.NOT_FOUND));
    }
}
