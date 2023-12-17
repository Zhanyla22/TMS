package com.example.TMS.dto.response;

import jakarta.validation.constraints.NotBlank;
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
public class CommentResponse {
    @NotBlank
    String commentAuthor;
    @NotBlank
    String description;
    @NotNull
    UUID commentUuid;
    @NotNull
    LocalDateTime createdDate;
}
