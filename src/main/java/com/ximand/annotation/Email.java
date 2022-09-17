package com.ximand.annotation;

import com.ximand.impl.spec.email.EmailSpec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that marks all fields whose should be checked for validity as email.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Email {

    EmailSpec value() default EmailSpec.HTML;

}
