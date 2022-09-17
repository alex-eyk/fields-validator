package com.ximand;

import com.ximand.annotation.Email;
import com.ximand.annotation.Validate;
import com.ximand.impl.spec.email.EmailSpec;

import javax.lang.model.element.Element;

class EmailValidateField extends ValidateField {

    private EmailSpec emailSpec;

    EmailValidateField(Element element, Validate validate, Email email) {
        super(element, validate);
        this.emailSpec = email.value();
    }

    public EmailSpec getEmailSpec() {
        return emailSpec;
    }

    public EmailValidateField setEmailSpec(EmailSpec emailSpec) {
        this.emailSpec = emailSpec;
        return this;
    }
}
