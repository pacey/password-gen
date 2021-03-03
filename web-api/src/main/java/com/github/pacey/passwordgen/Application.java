package com.github.pacey.passwordgen;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "Password Generator API",
        description = "A basic JSON API to generate passwords."
    )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.build(args)
            .banner(false)
            .start();
    }
}
