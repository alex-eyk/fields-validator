package com.ximand.validator.impl;

import com.ximand.spec.email.EmailSpecification;

public final class EmailValidator extends RegexValidator {

    public EmailValidator(EmailSpecification specification) {
        super(specification.toRegex());
    }

}
