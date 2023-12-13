package com.example.TMS.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfoExecutorResponse {

    @NotNull
    UUID uuid;

    @NotNull
    UsersResponseDto executor;
}
