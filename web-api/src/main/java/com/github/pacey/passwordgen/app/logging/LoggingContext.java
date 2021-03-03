package com.github.pacey.passwordgen.app.logging;

import org.slf4j.MDC;

import java.util.UUID;

public class LoggingContext {

    private LoggingContext() {}

    public static void setErrorId(UUID id) {
        MDC.put("error_id", id.toString());
    }
}
