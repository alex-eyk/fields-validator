package com.ximand.impl.spec;

/**
 * Specification of string, which contains information that can be used for it's validation.
 */
public interface Specification {

    /**
     * @return RegEx pattern for string validation
     */
    String toRegex();

}
