package com.ximand.annotation;

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
     *
     * @return Regular expression for validate field.
     */
    String value() default "";

}
