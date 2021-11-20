package com.ximand.impl.spec.email;

/**
 * In most cases the html standard is also redundant, most users do not use special characters,
 * which can simplify the verification even more.
 */
public final class SimpleEmailSpecification implements EmailSpecification {

    private static final String PATTERN = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    @Override
    public String toRegex() {
        return PATTERN;
    }
}
