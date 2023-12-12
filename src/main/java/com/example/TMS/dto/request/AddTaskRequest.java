package com.example.TMS.dto.request;

import com.example.TMS.entity.Users;
import com.example.TMS.enums.Priority;
import com.example.TMS.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AddTaskRequest {

    @NotNull
    String title;

    @NotNull
    String description;

    @NotNull
    Status status;

    @NotNull
    Priority priority;

    @NotNull
    Long executeId;
}
