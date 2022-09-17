package com.ximand.validator.impl;

import com.ximand.spec.password.PasswordSpecification;

public final class PasswordValidator extends RegexValidator {

    public PasswordValidator(PasswordSpecification passwordSpec) {
        super(passwordSpec.toRegex());
    }

}
