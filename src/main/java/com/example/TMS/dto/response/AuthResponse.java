package com.example.TMS.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @NotNull
    String jwt;

    @NotNull
    @JsonProperty("date_expired_access_token")
    Date dateExpiredAccessToken;

    @NotNull
    @JsonProperty("refresh_token")
    String refreshToken;

    @NotNull
    @JsonProperty("date_expired_refresh_token")
    Date dateExpiredRefreshToken;
}
