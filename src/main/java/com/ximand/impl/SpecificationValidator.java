package com.ximand.impl;

import com.ximand.Validator;
import com.ximand.impl.spec.Specification;

abstract class SpecificationValidator implements Validator<String> {

    private final String regex;

    public SpecificationValidator(Specification specification) {
        this.regex = specification.toRegex();
    }

    @Override
    public boolean validate(String validatable) {
        if (validatable != null && validatable.isEmpty() == false) {
            return validatable.matches(regex);
        }
        return false;
    }
}
