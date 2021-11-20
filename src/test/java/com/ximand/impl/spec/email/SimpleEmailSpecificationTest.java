package com.ximand.impl.spec.email;

import com.ximand.impl.EmailValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleEmailSpecificationTest {

    private final EmailValidator emailValidator = new EmailValidator(new SimpleEmailSpecification());

    @Test
    void emailTest() {
        assertTrue(emailValidator.validate("email@domain.com"));
        assertTrue(emailValidator.validate("em.ail@do.co"));
        assertTrue(emailValidator.validate("em_ail@d.co"));
    }

}