package com.ximand;

import com.ximand.annotation.Password;
import com.ximand.annotation.Validate;
import com.ximand.impl.spec.password.PasswordSpec;

import javax.lang.model.element.Element;

class PasswordValidateField extends ValidateField {

    private final PasswordSpec passwordSpec;
    private final int minLength;
    private final int maxLength;
    private final int lowerCase;
    private final int upperCase;
    private final int digits;
    private final int special;
    private final String allowedSpecial;

    PasswordValidateField(Element element, Validate validate, Password password) {
        super(element, validate);
        this.passwordSpec = password.value();
        this.minLength = password.minLength();
        this.maxLength = password.maxLength();
        this.lowerCase = password.lowerCase();
        this.upperCase = password.upperCase();
        this.digits = password.digits();
        this.special = password.special();
        this.allowedSpecial = password.allowedSpecial();
    }

    public PasswordSpec getPasswordSpec() {
        return passwordSpec;
    }

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getLowerCase() {
        return lowerCase;
    }

    public int getUpperCase() {
        return upperCase;
    }

    public int getDigits() {
        return digits;
    }

    public int getSpecial() {
        return special;
    }

    public String getAllowedSpecial() {
        return allowedSpecial;
    }
}
