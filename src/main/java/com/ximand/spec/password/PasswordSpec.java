package com.ximand.spec.password;

/**
 * @see PasswordSpecification
 */
public enum PasswordSpec {

    /**
     * Specification of password, which contains at least 8 characters, of which at least 1 character
     * and 1 digits.
     *
     * @see com.ximand.spec.password.impl.SimplePasswordSpecification
     */
    SIMPLE,

    /**
     * Specification of password, which contains at least 8 characters, of which at least 1 upper
     * case characters, 1 lower case characters, 1 digits and 1 special symbols '_', '.', '(', ')',
     * '[', ']', '|', '{', '}', '+', '@', '^', '$', '!', '/', '-', '%', '*', '#', '?', '{@literal &}', '\'.
     *
     * @see com.ximand.spec.password.impl.DefaultPasswordSpecification
     */
    DEFAULT,

    /**
     * Specification of password, which contains at least 10 characters, of which at least 2 upper
     * case characters, 2 lower case characters, 2 digits and 2 special symbols '_', '.', '(',
     * ')', '[', ']', '|', '{', '}', '+', '@', '^', '$', '!', '/', '-', '%', '*', '#', '?', '{@literal &}', '\'
     *
     * @see com.ximand.spec.password.impl.StrongPasswordSpecification
     */
    STRONG,

    /**
     * If prepared specifications {@code SIMPLE}, {@code DEFAULT} or {@code STRONG} are not suitable,
     * then you can use custom specification, setting the required number of characters yourself.
     *
     * @see com.ximand.spec.password.impl.CustomPasswordSpecification
     */
    CUSTOM
}
