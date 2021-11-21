package com.ximand;

import javax.lang.model.element.Element;

public class RegexValidateField extends ValidateField {

    private String regex;

    RegexValidateField(Element element, Validate validate, Regex regex) {
        super(element, validate);
        this.regex = regex.value();
    }

    public String getRegex() {
        return regex;
    }

    public RegexValidateField setRegex(String regex) {
        this.regex = regex;
        return this;
    }

}
