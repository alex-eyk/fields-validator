package com.ximand.impl.spec.email;

import com.ximand.impl.spec.Specification;

/**
 * Full compatible regular expression for validate email address created by standard RFC822 is
 * very ineffective and very large. Fortunately, the current specification for email addresses
 * is RFC 5322. {@see RfcEmailSpecification}
 * But RFC 5322 defines a syntax for email addresses that is both too strict before the '@' symbol,
 * too vague after the '@' symbol, and too weak (allows comments., spaces, and quoted strings,
 * which are unusual for most users). All this is taken into account and corrected by the W3C HTML
 * standard (https://html.spec.whatwg.org/multipage/input.html#valid-e-mail-address). Checking an
 * email address using this standard is more efficient. {@see HtmlEmailSpecification}
 * In most cases the html standard is also redundant, most users do not use special characters,
 * which can simplify the verification even more. {@see SimpleEmailSpecification}
 *
 * @see RfcEmailSpecification
 * @see HtmlEmailSpecification
 * @see SimpleEmailSpecification
 */
public interface EmailSpecification extends Specification {
}
