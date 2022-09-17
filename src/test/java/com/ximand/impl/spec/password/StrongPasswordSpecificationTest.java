package com.ximand.impl.spec.password;

import com.ximand.validator.impl.PasswordValidator;
import com.ximand.spec.password.impl.StrongPasswordSpecification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SpellCheckingInspection")
class StrongPasswordSpecificationTest {

    private final PasswordValidator validator = new PasswordValidator(new StrongPasswordSpecification());

    @Test
    void tooShortTest() {
        assertFalse(validator.validate("1"));
        assertFalse(validator.validate("1234567"));
        assertFalse(validator.validate("qwertyu"));
        assertFalse(validator.validate("12a3bCd"));
    }

    @Test
    void onlyDigitsTest() {
        assertFalse(validator.validate("1234567890"));
    }

    @Test
    void onlyCharsTest() {
        assertFalse(validator.validate("qwertyuiop"));
    }

    @Test
    void withOneDigitTest() {
        assertFalse(validator.validate("qwer!#UIop1"));
    }

    @Test
    void onlyDigitsAndCharsTest() {
        assertFalse(validator.validate("3311ab2cde12"));
    }

    @Test
    void withousUpperCaseTest() {
        assertFalse(validator.validate("1ab!2cder@2121"));
    }

    @Test
    void withOneUpperCaseTest() {
        assertFalse(validator.validate("1An!2fjvc@2121"));
    }

    @Test
    void withousLowerCaseTest() {
        assertFalse(validator.validate("1AB!2CDER@123"));
    }

    @Test
    void withOneLowerCaseTest() {
        assertFalse(validator.validate("1AB!2CDEr@123"));
    }

    @Test
    void withoutSpecialCharsTest() {
        assertFalse(validator.validate("123saWfvU21233"));
    }

    @Test
    void correctTest() {
        assertTrue(validator.validate("123saWWfvu2@d!"));
    }

}