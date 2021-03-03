package com.github.pacey.passwordgen.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.time.Instant;

@Value
@Schema(
    title = "Password Response",
    description = "Password response model containing the generated password and additional metadata."
)
public class PasswordResponse {

    @Schema(
        description = "Generated password.",
        example = "6JW{B\"j4)<145 (~"
    )
    String password;
    @Schema(description = "Time the password was generated.")
    Instant timestamp;
}
