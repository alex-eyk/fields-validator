package com.ximand.impl;

import com.ximand.Validator;
import com.ximand.impl.spec.Specification;

public class RegexValidator implements Validator<String> {

    private final String regex;

    public RegexValidator(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean validate(String validatable) {
        if (validatable != null && validatable.isEmpty() == false) {
            return validatable.matches(regex);
        }
        return false;
    }
}
