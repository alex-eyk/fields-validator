package com.ximand;

import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.Set;

final class ValidatorCodeGenerator {

    private static final String CLASS_NAME_SUFFIX = "Validator";

    private final Elements elementUtils;
    private final Filer filer;

    private TypeElement validateType;
    private Set<ValidateField> validateFields;

    ValidatorCodeGenerator(Elements elementUtils, Filer filer) {
        this.elementUtils = elementUtils;
        this.filer = filer;
    }

    void generate(TypeElement validateType, Set<ValidateField> validatableFields) throws IOException {
        this.validateType = validateType;
        this.validateFields = validatableFields;
        JavaFile file = JavaFile.builder(getElementPackage(), generateValidatorType())
                .indent("    ")
                .build();
        file.writeTo(filer);
    }

    private String getElementPackage() {
        final PackageElement packageElement = elementUtils.getPackageOf(validateType);
        if (packageElement.isUnnamed()) {
            throw new IllegalStateException("Unable to find package for type: " + validateType.getQualifiedName());
        } else {
            return packageElement.getQualifiedName().toString();
        }
    }

    private TypeSpec generateValidatorType() {
        final String typeName = validateType.getSimpleName() + CLASS_NAME_SUFFIX;
        return TypeSpec
                .classBuilder(typeName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(
                        ClassName.get(Validator.class), TypeName.get(validateType.asType())
                ))
                .addMethod(generateValidateMethod())
                .build();
    }

    private MethodSpec generateValidateMethod() {
        final ValidateMethodBuilder methodBuilder = new ValidateMethodBuilder(elementUtils, validateType);
        for (ValidateField validateField : validateFields) {
            final String getter =  findGetter(validateType, validateField.getElement());
            methodBuilder.addConditionsChecks(validateField, getter);
        }
        return methodBuilder.build();
    }

    private String findGetter(Element annotatedType, Element field) {
        final String getterName = ReflectUtils.getGetterMethodName(field.getSimpleName().toString());
        final String method = ReflectUtils.findMethod(elementUtils, annotatedType, getterName);
        if (method != null) {
            return method;
        } else {
            throw new IllegalStateException("Unable to find public getter: `" + getterName
                    + "` in class: `" + annotatedType + "`");
        }
    }
}
