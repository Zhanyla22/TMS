package com.example.TMS.dto.response;

import com.example.TMS.enums.Status;
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
public class CommentDeleteResponse {

    @NotNull
    UUID commentUuid;
    @NotNull
    Status status;
}
