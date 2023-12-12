package com.example.TMS.mapper;

import com.example.TMS.dto.response.UsersResponseDto;
import com.example.TMS.entity.Users;
import org.springframework.stereotype.Service;

@Service
public class UsersMapper {

    public static UsersResponseDto entityToResponse(Users users) {
        return UsersResponseDto.builder()
                .id(users.getId())
                .name(users.getName())
                .lastname(users.getLastname())
                .email(users.getEmail())
                .build();
    }
}
