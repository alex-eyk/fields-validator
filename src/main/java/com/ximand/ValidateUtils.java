package com.ximand;

public final class ValidateUtils {

    private ValidateUtils() {
    }

    public static int checkNotNegative(int i) throws IllegalArgumentException {
        if (i > 0) {
            return i;
        } else {
            throw new IllegalArgumentException("Value can't be negative");
        }
    }

}
