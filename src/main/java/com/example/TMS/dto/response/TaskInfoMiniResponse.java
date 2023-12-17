package com.example.TMS.dto.response;

import com.example.TMS.enums.StatusTask;
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
public class TaskInfoMiniResponse {
    @NotNull
    UUID uuid;

    @NotNull
    StatusTask statusTask;
}
