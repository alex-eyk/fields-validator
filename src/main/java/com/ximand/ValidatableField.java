package com.ximand;

import java.lang.reflect.Field;

public class ValidatableField {

    private final Field field;

    private boolean notNull = true;
    private int minSize = 0;
    private int maxSize = Integer.MAX_VALUE;

    public ValidatableField(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public ValidatableField setNotNull(boolean notNull) {
        this.notNull = notNull;
        return this;
    }

    public int getMinSize() {
        return minSize;
    }

    public ValidatableField setMinSize(int minSize) {
        this.minSize = minSize;
        return this;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public ValidatableField setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }
}
