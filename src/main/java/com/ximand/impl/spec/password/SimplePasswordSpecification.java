package com.ximand.impl.spec.password;

/**
 * Specification of password, which contains at least 8 characters, of which at least 1 character
 * and 1 digits.
 */
public final class SimplePasswordSpecification implements PasswordSpecification {

    private static final String PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d_\\.@$!%*#?&]{8,255}$";

    @Override
    public String toRegex() {
        return PATTERN;
    }

}
