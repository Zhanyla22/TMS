package com.example.TMS.dto.response;

import com.example.TMS.enums.Status;
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

    UUID commentUuid;

    Status status;
}
