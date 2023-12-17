package com.example.TMS.controller;

import com.example.TMS.dto.request.ConfirmCodeRequest;
import com.example.TMS.dto.request.RegistrationRequest;
import com.example.TMS.entity.User;
import com.example.TMS.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
@Order(1)
class RegistrationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UsersRepository usersRepository;

    @Test
    @Order(1)
    void registration() throws Exception {

        RegistrationRequest request = RegistrationRequest.builder()
                .email("test@gmail.com")
                .name("Asan")
                .lastname("Uson")
                .password("Password123!")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(2)
    void confirm() throws Exception {

        User user = usersRepository.findByEmail("test@gmail.com").orElse(null);

        assert user != null;
        ConfirmCodeRequest request = ConfirmCodeRequest.builder()
                .email(user.getEmail())
                .code(user.getRegistrationCode())
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/registration/confirm").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is2xxSuccessful());
    }
}