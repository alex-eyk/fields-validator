package com.ximand;

import com.ximand.annotation.Email;
import com.ximand.annotation.Password;
import com.ximand.annotation.Regex;
import com.ximand.annotation.UUID;
import com.ximand.annotation.Validate;

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
        final UUID uuid = element.getAnnotation(UUID.class);
        if (uuid != null) {
            return new UUIDValidateField(element, validate, uuid);
        }
        return new ValidateField(element, validate);
    }
}
