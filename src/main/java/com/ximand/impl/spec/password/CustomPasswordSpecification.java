package com.ximand.impl.spec.password;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.ximand.ValidateUtils.checkNotNegative;

/**
 * If prepared specifications ({@link DefaultPasswordSpecification}, {@link SimplePasswordSpecification}
 * or {@link StrongPasswordSpecification}) are not suitable for use, then you can use this custom
 * specification, setting the required number of characters yourself.
 */
public final class CustomPasswordSpecification implements PasswordSpecification {

    private static final Set<Character> RESERVED_REGEX = new HashSet<Character>() {{
        addAll(Arrays.asList('.', '(', ')', '[', ']', '|', '{', '}', '*', '+', '?', '^', '$', '/', '-', '\\'));
    }};

    private static final String DEFAULT_ALLOWED_SPEC_CHARS = "_\\.\\(\\)\\[\\]\\|\\{\\}\\+@\\^\\$!\\/\\-%%\\*#\\?&\\\\";

    private String allowedSpecialChars = DEFAULT_ALLOWED_SPEC_CHARS;

    private int minLength = 1;
    private int maxLength = 255;
    private int lowerCaseChars = 0;
    private int upperCaseChars = 0;
    private int digitChars = 0;
    private int specialChars = 0;

    /**
     * @param minLength Minimum length of password.
     * @throws IllegalArgumentException Length can not be negative.
     * @return A reference to this object.
     */
    public CustomPasswordSpecification setMinLength(int minLength) {
        this.minLength = checkNotNegative(minLength);
        return this;
    }

    /**
     * @param maxLength Maximum length of password.
     * @throws IllegalArgumentException Length can not be negative.
     * @return A reference to this object.
     */
    public CustomPasswordSpecification setMaxLength(int maxLength) {
        this.maxLength = checkNotNegative(maxLength);
        return this;
    }

    /**
     * @param lowerCaseChars minimum number of lower case chars (a-z) which should be contains password.
     * @throws IllegalArgumentException Number can not be negative.
     * @return A reference to this object.
     */
    public CustomPasswordSpecification setLowerCaseChars(int lowerCaseChars) {
        this.lowerCaseChars = checkNotNegative(lowerCaseChars);
        return this;
    }

    /**
     * @param upperCaseChars minimum number of upper case chars (A-Z) which should be contains password.
     * @throws IllegalArgumentException Number can not be negative.
     * @return A reference to this object.
     */
    public CustomPasswordSpecification setUpperCaseChars(int upperCaseChars) {
        this.upperCaseChars = checkNotNegative(upperCaseChars);
        return this;
    }

    /**
     * @param digitChars minimum number of digits which should be contains password.
     * @throws IllegalArgumentException Number can not be negative.
     * @return A reference to this object.
     */
    public CustomPasswordSpecification setDigitChars(int digitChars) {
        this.digitChars = checkNotNegative(digitChars);
        return this;
    }

    /**
     * Set minimum number of special characters in password. The following special characters can
     * be used: '_', '.', '(', ')', '[', ']', '|', '{', '}', '+', '@', '^', '$', '!', '/', '-', '%',
     * '*', '#', '?', '{@literal &}', '\'. These values can be changed using the methods
     * {@code setAllowedSpecialChars(String allowedSpecialChars)} or {@code setAllowedSpecialChars(char[] allowedSpecialChars)}.
     *
     * @param specialChars minimum number of special chars which should be contains password.
     * @throws IllegalArgumentException Number can not be negative.
     * @return A reference to this object.
     */
    public CustomPasswordSpecification setSpecialChars(int specialChars) {
        this.specialChars = checkNotNegative(specialChars);
        return this;
    }

    /**
     * Set special characters, that can be use in password. By default can be user: '_', '.', '(',
     * ')', '[', ']', '|', '{', '}', '+', '@', '^', '$', '!', '/', '-', '%', '*', '#', '?', '{@literal &}', '\'
     *
     * @param allowedSpecialChars A string of special characters that can be used (e.g. "@$!").
     * @return A reference to this object.
     */
    public CustomPasswordSpecification setAllowedSpecialChars(String allowedSpecialChars) {
        if (allowedSpecialChars == null) {
            this.allowedSpecialChars = "";
            return this;
        }
        return setAllowedSpecialChars(allowedSpecialChars.toCharArray());
    }

    /**
     * Set special characters, that can be use in password. By default can be user: '_', '.', '(',
     * ')', '[', ']', '|', '{', '}', '+', '@', '^', '$', '!', '/', '-', '%', '*', '#', '?', '{@literal &}', '\'
     *
     * @param allowedSpecialChars An array of special characters that can be used.
     * @return A reference to this object.
     */
    public CustomPasswordSpecification setAllowedSpecialChars(char[] allowedSpecialChars) {
        if (allowedSpecialChars == null) {
            this.allowedSpecialChars = "";
            return this;
        }
        final StringBuilder allowedCharsBuilder = new StringBuilder();
        for (char c : allowedSpecialChars) {
            if (RESERVED_REGEX.contains(c)) {
                allowedCharsBuilder
                        .append("\\")
                        .append(c);
            } else {
                allowedCharsBuilder
                        .append(c);
            }
        }
        this.allowedSpecialChars = allowedCharsBuilder
                .toString()
                .replace("%", "%%");
        return this;
    }

    /**
     * @return Custom password RegEx pattern.
     */
    @Override
    public String toRegex() {
        return new RegexBuilder()
                .appendfIfNotNegative("(?=(.*[a-z]){%d,})", lowerCaseChars)
                .appendfIfNotNegative("(?=(.*[A-Z]){%d,})", upperCaseChars)
                .appendfIfNotNegative("(?=(.*\\d){%d,})", digitChars)
                .appendfIfNotNegative("(?=(.*[" + allowedSpecialChars + "]){%d,})", specialChars)
                .appendf("[a-zA-Z\\d%s]", allowedSpecialChars)
                .appendf("{%d,%d}$", minLength, maxLength)
                .toString();
    }

    private static class RegexBuilder {

        private final StringBuilder builder = new StringBuilder();

        public RegexBuilder appendf(String format, Object... args) {
            builder.append(String.format(format, args));
            return this;
        }

        public RegexBuilder appendfIfNotNegative(String format, int num) {
            if (num > 0) {
                return appendf(format, num);
            } else {
                return this;
            }
        }

        @Override
        public String toString() {
            return builder.toString();
        }
    }
}
