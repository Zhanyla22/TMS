package com.example.TMS.dto.request;

import com.example.TMS.enums.Priority;
import com.example.TMS.enums.StatusTask;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "title can't be empty or null")
    @Schema(example = "CRUD for Task")
    String title;

    @Schema(example = "CRUD for TASK API")
    String description;

    StatusTask statusTask;

    Priority priority;

    @Schema(example = "1")
    Long executorId;
}
