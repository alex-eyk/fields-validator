package com.ximand;

import javax.lang.model.element.Element;

public final class ValidateFieldFactory {

    private ValidateFieldFactory() {
    }

    public static ValidateField createByValidate(Element element, Validate validate) {
        final Regex regex = element.getAnnotation(Regex.class);
        if (regex != null) {
            return new RegexValidateField(element, validate, regex);
        }
        final Email email = element.getAnnotation(Email.class);
        if (email != null) {
            return new EmailValidateField(element, validate, email);
        }
        final Password password = element.getAnnotation(Password.class);
        if (password != null) {
            return new PasswordValidateField(element, validate, password);
        }
        return new ValidateField(element, validate);
    }
}
