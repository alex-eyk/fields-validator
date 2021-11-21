package com.ximand.impl;

import com.ximand.impl.spec.password.PasswordSpecification;

public final class PasswordValidator extends RegexValidator {

    public PasswordValidator(PasswordSpecification passwordSpec) {
        super(passwordSpec.toRegex());
    }

}
