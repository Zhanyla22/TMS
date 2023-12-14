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
public class UpdateTaskRequest {
    @NotBlank(message = "title can't be empty or null")
    @Schema(example = "CRUD for Task")
    String title;

    @NotBlank(message = "description can't be empty or null")
    @Schema(example = "CRUD For task API ")
    String description;

    @NotBlank(message = "priority can't be empty or null")
    @Schema(example = "HIGH")
    Priority priority;

    @NotBlank(message = "statusTask can't be empty or null")
    @Schema(example = "PENDING")
    StatusTask statusTask;

    @NotBlank(message = "executorId can't be empty or null")
    @Schema(example = "1")
    Long executorId;
}
