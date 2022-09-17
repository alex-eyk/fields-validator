package com.ximand.spec.email.impl;

import com.ximand.spec.email.EmailSpec;
import com.ximand.spec.email.EmailSpecification;

public final class EmailSpecificationFactory {

    private EmailSpecificationFactory() {

    }

    public static EmailSpecification createBySpec(EmailSpec specification) {
        switch (specification) {
            case SIMPLE:
                return new SimpleEmailSpecification();
            case HTML:
                return new HtmlEmailSpecification();
            case RFC:
                return new RfcEmailSpecification();
            default:
                throw new IllegalStateException("No impl for this specification: " + specification);
        }
    }

}
