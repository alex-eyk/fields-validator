package com.ximand.annotation;

import com.ximand.impl.spec.password.PasswordSpec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that marks all fields whose should be checked for validity as password.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Password {

    /**
     * Specification used for password validation. There are ready-to-use implementations
     * {@code SIMPLE}, {@code DEFAULT} and {@code STRONG}.
     * Also exists {@code CUSTOM} implementation. If it user, should be passed values
     * {@code minLength}, {@code maxLength}, {@code lowerCase}, etc.
     *
     * @return Specification of validatable password.
     */
    PasswordSpec value() default PasswordSpec.DEFAULT;

    int minLength() default 1;

    int maxLength() default 255;

    int lowerCase() default 0;

    int upperCase() default 0;

    int digits() default 0;

    int special() default 0;

    String allowedSpecial() default "_.()[]|{}+@^$!/-%%*#?&\\";

}
