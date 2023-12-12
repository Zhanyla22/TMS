package com.example.TMS.dto.request;

import com.example.TMS.enums.Priority;
import com.example.TMS.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTaskRequest {
    String title;
    String description;
    Priority priority;
    Status status;
    Long executeId;
}
