package com.github.pacey.passwordgen.app.provider;

import java.util.Random;

@FunctionalInterface
public interface RandomProvider {

    Random random();
}
