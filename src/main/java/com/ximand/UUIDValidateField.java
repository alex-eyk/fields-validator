package com.ximand;

import com.ximand.annotation.UUID;
import com.ximand.annotation.Validate;

import javax.lang.model.element.Element;

public class UUIDValidateField extends ValidateField {

    private final int version;

    UUIDValidateField(Element element, Validate validate, UUID uuid) {
        super(element, validate);
        this.version = uuid.version();
    }

    public int getVersion() {
        return version;
    }
}
