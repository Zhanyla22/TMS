package com.example.TMS.dto.response;

import com.example.TMS.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDeleteResponse {

    String uuid;

    Status status;
}
