package com.example.TMS.dto.request;

import com.example.TMS.enums.Priority;
import com.example.TMS.enums.StatusTask;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    StatusTask statusTask;

    @NotNull
    Priority priority;

    @NotNull
    Long executorId;
}
