package com.ximand.validator.impl;

import com.ximand.spec.uuid.impl.UUIDSpecificationImpl;

public final class UUIDValidator extends RegexValidator {

    public UUIDValidator(int version) {
        super(new UUIDSpecificationImpl(version).toRegex());
    }
}
