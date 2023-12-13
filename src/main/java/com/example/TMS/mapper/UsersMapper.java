package com.example.TMS.mapper;

import com.example.TMS.dto.response.UsersResponseDto;
import com.example.TMS.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UsersMapper {

    public static UsersResponseDto entityToResponse(User user) {
        return UsersResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .build();
    }
}
