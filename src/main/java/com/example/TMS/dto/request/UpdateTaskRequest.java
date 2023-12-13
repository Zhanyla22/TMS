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
public class UpdateTaskRequest {
    @NotNull
    String title;
    @NotNull
    String description;
    @NotNull
    Priority priority;
    @NotNull
    StatusTask statusTask;
    @NotNull
    Long executorId;
}
