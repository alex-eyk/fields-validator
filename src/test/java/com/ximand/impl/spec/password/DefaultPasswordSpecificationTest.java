package com.ximand.impl.spec.password;

import com.ximand.validator.impl.PasswordValidator;
import com.ximand.spec.password.impl.DefaultPasswordSpecification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SpellCheckingInspection")
class DefaultPasswordSpecificationTest {

    private final PasswordValidator validator = new PasswordValidator(new DefaultPasswordSpecification());

    @Test
    void tooShortTest() {
        assertFalse(validator.validate("1"));
        assertFalse(validator.validate("1234567"));
        assertFalse(validator.validate("qwertyu"));
        assertFalse(validator.validate("12a3bCd"));
    }

    @Test
    void onlyDigitsTest() {
        assertFalse(validator.validate("12345678"));
    }

    @Test
    void onlyCharsTest() {
        assertFalse(validator.validate("qwertyui"));
    }

    @Test
    void onlyDigitsAndCharsTest() {
        assertFalse(validator.validate("3311ab2cde"));
    }

    @Test
    void withousUpperCaseTest() {
        assertFalse(validator.validate("1ab!2cder@"));
    }

    @Test
    void withousLowerCaseTest() {
        assertFalse(validator.validate("1AB!2CDER@"));
    }

    @Test
    void withoutSpecialCharsTest() {
        assertFalse(validator.validate("123saWfvu2"));
    }

    @Test
    void correctTest() {
        assertTrue(validator.validate("123saWfvu2@d!"));
    }

}