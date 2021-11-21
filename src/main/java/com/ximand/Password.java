package com.ximand;

import com.ximand.impl.spec.password.PasswordSpec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Password {

    PasswordSpec value() default PasswordSpec.DEFAULT;

    int minLength() default 0;

    int maxLength() default 0;

    int lowerCase() default 0;

    int upperCase() default 0;

    int digits() default 0;

    int special() default 0;

    String allowedSpecial() default "_\\.\\(\\)\\[\\]\\|\\{\\}\\+@\\^\\$!\\/\\-%%\\*#\\?&\\\\";

}
