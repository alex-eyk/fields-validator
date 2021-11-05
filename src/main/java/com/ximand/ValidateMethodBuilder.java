package com.ximand;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.util.List;

class ValidateMethodBuilder {

    private static final String VALIDATE_METHOD_NAME = "validate";
    private static final String VALIDATE_METHOD_PARAM = "validatable";
    private static final String[] SIZE_MEMBERS = new String[]{
            "length", "length()", "size()"
    };

    private final MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(VALIDATE_METHOD_NAME);
    private final Elements elementUtils;

    ValidateMethodBuilder(Elements elementUtils, TypeElement validateType) {
        this.elementUtils = elementUtils;
        initMethodSignature(validateType);
    }

    private void initMethodSignature(TypeElement validateType) {
        final TypeName typeName = TypeName.get(validateType.asType());
        methodBuilder
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(typeName, VALIDATE_METHOD_PARAM)
                .returns(boolean.class);
    }

    void addConditionsChecks(ValidateField validateField) {
        addNotNullChecks(validateField);
        final int minSize = validateField.getMinSize();
        if (minSize != ValidateField.DEF_MIN_SIZE) {
            addSizeCheck(">", minSize, validateField.isNotNull(), validateField.getElement());
        }
        final int maxSize = validateField.getMaxSize();
        if (maxSize != ValidateField.DEF_MAX_SIZE) {
            addSizeCheck("<", maxSize, validateField.isNotNull(), validateField.getElement());
        }
    }

    private void addNotNullChecks(ValidateField validateField) {
        if (validateField.isNotNull() == false) {
            return;
        }
        final String getter = ReflectUtils.getGetterMethodName(
                validateField.getElement().getSimpleName().toString()
        );
        methodBuilder
                .beginControlFlow("if (validatable." + getter + " == null)")
                .addStatement("return false")
                .endControlFlow();
    }

    private void addSizeCheck(
            String inverseOperator, int size, boolean notNull, Element validElement
    ) {
        final StringBuilder sizeControlFlowBuilder = new StringBuilder("if (");
        final String getter = ReflectUtils.getGetterMethodName(
                validElement.getSimpleName().toString()
        );
        final String sizeMethod = findSizeMethod(validElement);
        if (notNull == false) {
            sizeControlFlowBuilder.append(
                    String.format("validatable.%s != null && ", getter)
            );
        }
        sizeControlFlowBuilder.append(
                String.format("validatable.%s.%s %s %d)", getter, sizeMethod, inverseOperator, size)
        );
        methodBuilder.beginControlFlow(sizeControlFlowBuilder.toString())
                .addStatement("return false")
                .endControlFlow();
    }

    private String findSizeMethod(Element element) {
        final List<? extends Element> allMembers = elementUtils.getAllMembers(getElementType(element));
        for (Element member : allMembers) {
            final String name = member.toString();
            for (String sizeMember : SIZE_MEMBERS) {
                if (name != null && name.equals(sizeMember)
                        && member.getModifiers().contains(Modifier.PUBLIC)) {
                    return sizeMember;
                }
            }
        }
        throw new IllegalStateException("Object (type: " + element + ") that limited in size " +
                "should contains size method (size(), length() or length public field)");
    }

    private TypeElement getElementType(Element element) {
        final TypeMirror typeMirror = element.asType();
        return elementUtils.getTypeElement(typeMirror.toString());
    }

    MethodSpec build() {
        return methodBuilder
                .addStatement("return true")
                .build();
    }

}
