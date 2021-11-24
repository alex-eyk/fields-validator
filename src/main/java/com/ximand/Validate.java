package com.ximand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that marks all fields whose values can be checked for validity. If field not
 * annotated, its value will not be validate during validating the object.
 *
 * @author Aleksej Kiselev
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface Validate {

    /**
     * @return {@code true}, if value can not be null, in other case {@code false}.
     */
    boolean notNull() default false;

    /**
     * This property works for {@code String} (in this case, the number of characters in the string
     * will be validate, {@code Collection} and arrays.
     *
     * @return Minimum size of value.
     */
    int minSize() default 0;

    /**
     * This property works for {@code String} (in this case, the number of characters in the string
     * will be validate, {@code Collection} and array.
     *
     * @return Maximum size of value.
     */
    int maxSize() default Integer.MAX_VALUE;

}
