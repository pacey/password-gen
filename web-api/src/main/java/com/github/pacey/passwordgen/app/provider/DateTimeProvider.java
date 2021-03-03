package com.github.pacey.passwordgen.app.provider;

import java.time.Instant;

@FunctionalInterface
public interface DateTimeProvider {

    Instant now();
}
