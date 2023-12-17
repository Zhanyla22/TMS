package com.example.TMS.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {

    @NotBlank(message = "email can't be empty or null")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    String email;

    @NotBlank(message = "password can't be empty or null")
    @Schema(example = "Password123!")
    @Size(min = 5)
    String password;

    @Schema(example = "admin")
    String name;

    @Schema(example = "admin")
    String lastname;
}
