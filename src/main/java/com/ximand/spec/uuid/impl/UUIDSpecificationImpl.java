package com.ximand.spec.uuid.impl;

import com.ximand.ValidateUtils;
import com.ximand.spec.uuid.UUIDSpecification;

public class UUIDSpecificationImpl implements UUIDSpecification {

    private static final String UUID_REGEX_FIRST_PART = "/^[0-9A-F]{8}-[0-9A-F]{4}-[";
    private static final String UUID_REGEX_LAST_PART = "][0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}$/i";

    private final String regex;

    public UUIDSpecificationImpl(int version) {
        ValidateUtils.checkNotNegative(version);
        if (version > 5) {
            throw new IllegalArgumentException("UUID version can not be > 5");
        }
        this.regex = UUID_REGEX_FIRST_PART + version + UUID_REGEX_LAST_PART;
    }

    @Override
    public String toRegex() {
        return regex;
    }
}
