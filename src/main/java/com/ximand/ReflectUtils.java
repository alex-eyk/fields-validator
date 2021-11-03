package com.ximand;

import java.lang.reflect.Method;

public final class ReflectUtils {

    private ReflectUtils() {
    }

    public static String getExistsGetterName(Class<?> class_, String field) {
        final String getter = getGetterMethodName(field);
        for (Method method : class_.getDeclaredMethods()) {
            if (method.getName().equals(getter)) {
                return getter;
            }
        }
        throw new IllegalStateException("Unable to create validator, class: " + class_.getName()
                + " should have getter (" + getter + ") for field: " + field);
    }

    public static String getGetterMethodName(String field) {
        final char firstChar = Character.toUpperCase(field.charAt(0));
        return "get" + firstChar + field.substring(1);
    }

    public static boolean isAssignableFromInt(Class<?> sourceClass) {
        return isAssignableFromOneOf(sourceClass, int.class, Integer.class);
    }

    public static boolean isAssignableFromOneOf(Class<?> sourceClass, Class<?>... classes) {
        for (Class<?> class_ : classes) {
            if (sourceClass.isAssignableFrom(class_)) {
                return true;
            }
        }
        return false;
    }

}
