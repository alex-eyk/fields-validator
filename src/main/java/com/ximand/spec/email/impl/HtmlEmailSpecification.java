package com.ximand.spec.email.impl;

import com.ximand.spec.email.EmailSpecification;

/**
 * RFC 5322 defines a syntax for email addresses that is both too strict before the '@' symbol,
 * too vague after the '@' symbol, and too weak (allows comments., spaces, and quoted strings,
 * which are unusual for most users). All this is taken into account and corrected by the W3C HTML
 * standard (https://html.spec.whatwg.org/multipage/input.html#valid-e-mail-address). Checking an
 * email address using this standard is more efficient.
 */
public final class HtmlEmailSpecification implements EmailSpecification {

    private static final String PATTERN = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}" +
            "[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    @Override
    public String toRegex() {
        return PATTERN;
    }
}
