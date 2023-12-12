package com.example.TMS.dto.response;

import com.example.TMS.enums.Priority;
import com.example.TMS.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    Status status;

    @NotNull
    Priority priority;

    @NotNull
    UsersResponseDto execute;

    @NotNull
    UsersResponseDto author;

    @NotNull
    String uuid;

    @NotNull
    Long id;

    @NotNull
    LocalDateTime createdDate;
}
