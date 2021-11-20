package com.ximand.impl.spec.password;

/**
 * Specification of password, which contains at least 8 characters, of which at least 1 upper
 * case characters, 1 lower case characters, 1 digits and 1 special symbols ('_', '.', '@', '$',
 * '!', '%', '*', '#', '?', '&').
 */
public final class DefaultPasswordSpecification implements PasswordSpecification {

    private static final String PATTERN = "(?-i)^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[_\\.@$!%*#?&])[A-Za-z\\d_\\.@$!%*#?&]{8,255}$";

    @Override
    public String toRegex() {
        return PATTERN;
    }
}
