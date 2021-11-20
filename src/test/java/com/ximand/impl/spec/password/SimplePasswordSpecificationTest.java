package com.ximand.impl.spec.password;

import com.ximand.impl.PasswordValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SpellCheckingInspection")
class SimplePasswordSpecificationTest {

    private final PasswordValidator validator = new PasswordValidator(new SimplePasswordSpecification());

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
    void correctTest() {
        assertTrue(validator.validate("12a3bc4dd"));
    }

}