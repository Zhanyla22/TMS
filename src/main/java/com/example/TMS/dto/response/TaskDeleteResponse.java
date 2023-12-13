package com.example.TMS.dto.response;

import com.example.TMS.enums.Status;
import com.example.TMS.enums.StatusTask;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDeleteResponse {

    UUID uuid;

    Status status;
}
