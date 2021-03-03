package com.github.pacey.passwordgen.app.provider;

import java.util.UUID;

@FunctionalInterface
public interface IdentifierProvider {

    UUID identifier();
}
