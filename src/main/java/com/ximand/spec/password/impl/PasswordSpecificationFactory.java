package com.ximand.spec.password.impl;

import com.ximand.spec.password.PasswordSpec;
import com.ximand.spec.password.PasswordSpecification;

public final class PasswordSpecificationFactory {

    private PasswordSpecificationFactory() {
    }

    public static PasswordSpecification createBySpec(PasswordSpec specification) {
        switch (specification) {
            case SIMPLE:
                return new SimplePasswordSpecification();
            case DEFAULT:
                return new DefaultPasswordSpecification();
            case STRONG:
                return new StrongPasswordSpecification();
            default:
                throw new IllegalStateException("No impl for this specification: " + specification);
        }
    }
}
