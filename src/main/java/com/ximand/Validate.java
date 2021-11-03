package com.ximand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {

    boolean notNull() default false;

    int minSize() default 0;

    int maxSize() default Integer.MAX_VALUE;

}
