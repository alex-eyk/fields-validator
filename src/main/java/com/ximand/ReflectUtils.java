package com.ximand;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.util.List;

final class ReflectUtils {

    private ReflectUtils() {
    }

    static String getGetterMethodName(String field) {
        final char firstChar = Character.toUpperCase(field.charAt(0));
        return "get" + firstChar + field.substring(1) + "()";
    }

    static String findMethod(Elements elementUtils, Element field, String... methods) {
        final List<? extends Element> allMembers = elementUtils.getAllMembers(getElementType(elementUtils, field));
        for (Element member : allMembers) {
            final String name = member.toString();
            for (String method : methods) {
                if (name != null && name.equals(method)
                        && member.getModifiers().contains(Modifier.PUBLIC)) {
                    return method;
                }
            }
        }
        return null;
    }

    private static TypeElement getElementType(Elements elementUtils, Element element) {
        final TypeMirror typeMirror = element.asType();
        return elementUtils.getTypeElement(typeMirror.toString());
    }

}
