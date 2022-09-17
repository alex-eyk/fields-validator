package com.ximand.spec.password.impl;

import com.ximand.spec.password.PasswordSpecification;

/**
 * Specification of password, which contains at least 10 characters, of which at least 2 upper
 * case characters, 2 lower case characters, 2 digits and 2 special symbols ('_', '.', '(',
 * ')', '[', ']', '|', '{', '}', '+', '@', '^', '$', '!', '/', '-', '%', '*', '#', '?', '{@literal &}', '\').
 */
public final class StrongPasswordSpecification implements PasswordSpecification {

    private static final String PATTERN = "(?-i)^(?=(.*[a-z]){2,})(?=(.*[A-Z]){2,})(?=(.*\\d){2,})" +
            "(?=(.*[_\\.\\(\\)\\[\\]\\|\\{\\}\\+@\\^\\$!\\/\\-%%\\*#\\?&\\\\]){2,})[A-Za-z\\d_\\.\\" +
            "(\\)\\[\\]\\|\\{\\}\\+@\\^\\$!\\/\\-%%\\*#\\?&\\\\]{10,255}$";

    @Override
    public String toRegex() {
        return PATTERN;
    }
}
