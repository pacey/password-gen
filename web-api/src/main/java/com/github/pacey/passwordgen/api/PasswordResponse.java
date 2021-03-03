package com.github.pacey.passwordgen.api;

import lombok.Value;

import java.time.Instant;

@Value
public class PasswordResponse {

    String password;
    Instant timestamp;
}
