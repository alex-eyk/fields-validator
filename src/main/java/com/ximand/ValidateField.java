package com.ximand;

import com.ximand.annotation.Validate;

import javax.lang.model.element.Element;

class ValidateField {

    static final int DEF_MIN_SIZE = 0;
    static final int DEF_MAX_SIZE = Integer.MAX_VALUE;

    private final Element element;

    private final boolean notNull;
    private final int minSize;
    private final int maxSize;

    ValidateField(Element element, Validate validate) {
        this.element = element;
        this.notNull = validate.notNull();
        this.minSize = validate.minSize();
        this.maxSize = validate.maxSize();
    }

    Element getElement() {
        return element;
    }

    boolean isNotNull() {
        return notNull;
    }

    int getMinSize() {
        return minSize;
    }

    int getMaxSize() {
        return maxSize;
    }

}
