package com.ximand;

import com.ximand.annotation.Regex;
import com.ximand.annotation.Validate;

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

    /**
     * @deprecated Use constructor parameters to set regex.
     */
    @Deprecated
    public RegexValidateField setRegex(String regex) {
        this.regex = regex;
        return this;
    }

}
