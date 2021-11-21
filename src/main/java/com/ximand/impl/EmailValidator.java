package com.ximand.impl;

import com.ximand.impl.spec.email.EmailSpecification;

public final class EmailValidator extends RegexValidator {

    public EmailValidator(EmailSpecification specification) {
        super(specification.toRegex());
    }

}
