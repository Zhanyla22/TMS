package com.example.TMS.dto.response;

import com.example.TMS.enums.Priority;
import com.example.TMS.enums.Status;
import com.example.TMS.enums.StatusTask;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfoTaskResponse {

    @NotNull
    String title;

    @NotNull
    String description;

    @NotNull
    StatusTask statusTask;

    @NotNull
    Status status;

    @NotNull
    Priority priority;

    @NotNull
    UsersResponseDto executor;

    @NotNull
    UsersResponseDto author;

    @NotNull
    UUID uuid;

    @NotNull
    Long id;

    @NotNull
    LocalDateTime createdDate;
}
