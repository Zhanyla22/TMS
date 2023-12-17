package com.example.TMS.service.impl;

import com.example.TMS.dto.request.ConfirmCodeRequest;
import com.example.TMS.dto.request.RegistrationRequest;
import com.example.TMS.entity.User;
import com.example.TMS.enums.Role;
import com.example.TMS.enums.Status;
import com.example.TMS.exception.BaseException;
import com.example.TMS.exception.common.UserNotFoundException;
import com.example.TMS.repository.UsersRepository;
import com.example.TMS.service.UsersService;
import com.example.TMS.util.EmailUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersServiceImpl implements UsersService, UserDetailsService {

    UsersRepository usersRepository;
    PasswordEncoder passwordEncoder;
    EmailUtil emailUtil;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return usersRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email, HttpStatus.NOT_FOUND));
    }

    @Override
    public String registration(RegistrationRequest request) {
        if (usersRepository.existsByEmail(request.getEmail())) {
            throw new BaseException("user already registered", HttpStatus.IM_USED);
        }
        String genCode = codeGenerate();
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .status(Status.WAITING_FOR_CODE)
                .name(request.getName())
                .lastname(request.getLastname())
                .registrationCode(genCode)
                .build();
        usersRepository.save(user);
        emailUtil.send(request.getEmail(), "confirmation code for TMS by Zhanylai", genCode);
        return "code sent to your email " + request.getEmail() + "confirm it";
    }

    @Override
    public String confirm(ConfirmCodeRequest confirmCodeRequest) {
        User user = usersRepository.findByEmail(confirmCodeRequest.getEmail()).orElseThrow(
                () -> new UserNotFoundException(confirmCodeRequest.getEmail(), HttpStatus.NOT_FOUND)
        );
        if (!user.getRegistrationCode().equals(confirmCodeRequest.getCode())) {
            throw new BaseException("code is not valid", HttpStatus.NOT_ACCEPTABLE);
        }
        user.setStatus(Status.ACTIVE);
        usersRepository.save(user);
        return "your email is activated";
    }


    public String codeGenerate() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replaceAll("-", "");

        return randomUUIDString.substring(0, 6);
    }
}
