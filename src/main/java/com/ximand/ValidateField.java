package com.ximand;

import javax.lang.model.element.Element;

class ValidateField {

    static final int DEF_MIN_SIZE = 0;
    static final int DEF_MAX_SIZE = Integer.MAX_VALUE;

    private final Element element;

    private boolean notNull = true;
    private int minSize = DEF_MIN_SIZE;
    private int maxSize = DEF_MAX_SIZE;

    ValidateField(Element element) {
        this.element = element;
    }

    static ValidateField createByValidate(Element element, Validate validate) {
        return new ValidateField(element)
                .setNotNull(validate.notNull())
                .setMinSize(validate.minSize())
                .setMaxSize(validate.maxSize());
    }

    Element getElement() {
        return element;
    }

    boolean isNotNull() {
        return notNull;
    }

    ValidateField setNotNull(boolean notNull) {
        this.notNull = notNull;
        return this;
    }

    int getMinSize() {
        return minSize;
    }

    ValidateField setMinSize(int minSize) {
        this.minSize = minSize;
        return this;
    }

    int getMaxSize() {
        return maxSize;
    }

    ValidateField setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }
}
