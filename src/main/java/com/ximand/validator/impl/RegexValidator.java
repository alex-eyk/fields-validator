package com.ximand.validator.impl;

import com.ximand.validator.Validator;

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
