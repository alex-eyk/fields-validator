package com.ximand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that marks all fields whose values can be checked for validity using
 * regular expressions.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Regex {

    /**
     * Regular expression witch will use in {@code validate(T t)} method.
     */
    String value() default "";

}
