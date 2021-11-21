package com.ximand.impl.spec.email;

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
