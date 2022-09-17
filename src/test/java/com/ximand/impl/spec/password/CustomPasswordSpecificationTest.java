package com.ximand.impl.spec.password;

import com.ximand.validator.impl.PasswordValidator;
import com.ximand.spec.password.impl.CustomPasswordSpecification;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomPasswordSpecificationTest {

    @Test
    void defValuesTest() {
        final PasswordValidator passwordValidator = new PasswordValidator(new CustomPasswordSpecification());
        assertTrue(passwordValidator.validate(generateSimpleString(255)));
        assertTrue(passwordValidator.validate(generateSimpleString(1)));
        assertFalse(passwordValidator.validate(generateSimpleString(256)));
        assertFalse(passwordValidator.validate(generateSimpleString(0)));
        assertTrue(passwordValidator.validate("#@!"));
    }

    @Test
    void minLengthTest() {
        for (int i = 1; i < 20; i++) {
            final PasswordValidator passwordValidator = new PasswordValidator(
                    new CustomPasswordSpecification()
                            .setMinLength(i)
            );
            assertFalse(passwordValidator.validate(generateSimpleString(i - 1)));
            assertTrue(passwordValidator.validate(generateSimpleString(i)));
        }
    }

    @Test
    void maxLengthTest() {
        for (int i = 2; i < 20; i++) {
            final PasswordValidator passwordValidator = new PasswordValidator(
                    new CustomPasswordSpecification()
                            .setMaxLength(i)
            );
            assertFalse(passwordValidator.validate(generateSimpleString(i + 1)));
            assertTrue(passwordValidator.validate(generateSimpleString(i)));
        }
    }

    @Test
    void digitsTest() {
        for (int i = 1; i < 20; i++) {
            final PasswordValidator passwordValidator = new PasswordValidator(
                    new CustomPasswordSpecification()
                            .setDigitChars(i)
            );
            assertFalse(passwordValidator.validate(generateSimpleString(i - 1, 1, 1, 1)));
            assertTrue(passwordValidator.validate(generateSimpleString(i, 1, 1, 1)));
            assertTrue(passwordValidator.validate(generateSimpleString(i + 1, 1, 1, 1)));
        }
    }

    @Test
    void upperTest() {
        for (int i = 1; i < 20; i++) {
            final PasswordValidator passwordValidator = new PasswordValidator(
                    new CustomPasswordSpecification()
                            .setUpperCaseChars(i)
            );
            assertFalse(passwordValidator.validate(generateSimpleString(1, i - 1, 1, 1)));
            assertTrue(passwordValidator.validate(generateSimpleString(1, i, 1, 1)));
            assertTrue(passwordValidator.validate(generateSimpleString(1, i + 1, 1, 1)));
        }
    }

    @Test
    void lowerTest() {
        for (int i = 1; i < 20; i++) {
            final PasswordValidator passwordValidator = new PasswordValidator(
                    new CustomPasswordSpecification()
                            .setLowerCaseChars(i)
            );
            assertFalse(passwordValidator.validate(generateSimpleString(1, 1, i - 1, 1)));
            assertTrue(passwordValidator.validate(generateSimpleString(1, 1, i, 1)));
            assertTrue(passwordValidator.validate(generateSimpleString(1, 1, i + 1, 1)));
        }
    }

    @Test
    void specialNumberTest() {
        for (int i = 1; i < 20; i++) {
            final PasswordValidator passwordValidator = new PasswordValidator(
                    new CustomPasswordSpecification()
                            .setSpecialChars(i)
            );
            assertFalse(passwordValidator.validate(generateSimpleString(1, 1, 1, i - 1)));
            assertTrue(passwordValidator.validate(generateSimpleString(1, 1, 1, i)));
            assertTrue(passwordValidator.validate(generateSimpleString(1, 1, 1, i + 1)));
        }
    }

    @Test
    void specialCharsTest() {
        final String allSpecialPassword = "_.[]()|{}+@^$!/-%*#?&\\";
        final PasswordValidator passwordValidator = new PasswordValidator(
                new CustomPasswordSpecification()
                        .setSpecialChars(22)
        );
        assertTrue(passwordValidator.validate(allSpecialPassword));
    }

    @Test
    void disableSpecialCharsTest() {
        final PasswordValidator passwordValidator = new PasswordValidator(
                new CustomPasswordSpecification()
                        .setAllowedSpecialChars("")
        );
        assertFalse(passwordValidator.validate("@ad$f"));
    }

    private String generateSimpleString(int length) {
        return generateSimpleString(length, '1');
    }

    private String generateSimpleString(int digits, int upper, int lower, int special) {
        return generateSimpleString(digits, '1') + generateSimpleString(upper, 'A')
                + generateSimpleString(lower, 'a') + generateSimpleString(special, '@');
    }

    private String generateSimpleString(int length, char c) {
        char[] chars = new char[length];
        Arrays.fill(chars, c);
        return new String(chars);
    }

}