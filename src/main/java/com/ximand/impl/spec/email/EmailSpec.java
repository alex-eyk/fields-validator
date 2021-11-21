package com.ximand.impl.spec.email;

/**
 * @see EmailSpecification
 */
public enum EmailSpec {

    /**
     * Validation using a simple small regular expression, that can validate almost all email
     * addresses, but does not correspond to the standard. Can recognize some existing email
     * addresses as invalid, however the probability is very low.
     *
     * @see SimpleEmailSpecification
     */
    SIMPLE,

    /**
     * Validation using regular expression, that correspond to W3C HTML standard. This
     * standard was created because of RFC standard is very redundancy. Regular expression
     * is larger than {@code SIMPLE}.
     *
     * @see HtmlEmailSpecification
     */
    HTML,

    /**
     * Validation using very large regular expression, that correspond to RFC standard. Works
     * with all email addresses, but very redundancy.
     *
     * @see RfcEmailSpecification
     */
    RFC
}
