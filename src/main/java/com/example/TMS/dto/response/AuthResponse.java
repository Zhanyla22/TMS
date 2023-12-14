package com.example.TMS.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {

    @NotBlank(message = "jwt can't be empty or null")
    String jwt;

    @NotNull
    @JsonProperty("date_expired_access_token")
    Date dateExpiredAccessToken;

    @NotBlank(message = "refreshToken can't be empty or null")
    @JsonProperty("refresh_token")
    String refreshToken;

    @NotNull
    @JsonProperty("date_expired_refresh_token")
    Date dateExpiredRefreshToken;
}
