package com.ximand;

final class ReflectUtils {

    private ReflectUtils() {
    }

    static String getGetterMethodName(String field) {
        final char firstChar = Character.toUpperCase(field.charAt(0));
        return "get" + firstChar + field.substring(1) + "()";
    }

}
