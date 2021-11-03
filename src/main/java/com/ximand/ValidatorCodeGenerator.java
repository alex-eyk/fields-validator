package com.ximand;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.List;

public class ValidatorCodeGenerator {

    private static final String VALIDATE_METHOD_NAME = "validate";
    private static final String CLASS_NAME_SUFFIX = "Validator";

    private final List<ValidatableField> validatableFields;
    private final MethodSpec.Builder validateMethodBuilder;

    private Class<?> validatableClass;

    public ValidatorCodeGenerator(List<ValidatableField> validatableFields) {
        this.validatableFields = validatableFields;
        this.validateMethodBuilder = MethodSpec.methodBuilder(VALIDATE_METHOD_NAME);
    }

    public <T> void generate(Class<T> validatableClass) throws IOException {
        this.validatableClass = validatableClass;
        final String packageName = validatableClass.getPackageName();
        initMethodSignature(validatableClass);
        for (ValidatableField validatableField : validatableFields) {
            addNotNullCheck(validatableField);
            addSizeChecks(validatableField);
        }
        validateMethodBuilder.addStatement("return true");
        final String className = validatableClass.getSimpleName() + CLASS_NAME_SUFFIX;

        final TypeSpec typeSpec = TypeSpec
                .classBuilder(className)
                .addSuperinterface(ParameterizedTypeName.get(
                        ClassName.get(Validator.class), TypeName.get(validatableClass))
                )
                .addMethod(validateMethodBuilder.build())
                .build();
        JavaFile.builder(packageName, typeSpec)
                .build()
                .writeTo(Path.of("/Users/aleksejkiselev/IdeaProjects/fields-validator/src/main/java/"));
    }

    private void initMethodSignature(Type validatableType) {
        validateMethodBuilder
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(validatableType, "validatable")
                .returns(boolean.class);
    }

    private void addNotNullCheck(ValidatableField validatableField) {
        if (validatableField.isNotNull() == false) {
            return;
        }
        final String getter = ReflectUtils.getExistsGetterName(
                validatableClass,
                validatableField.getField().getName()
        ) + "()";
        validateMethodBuilder
                .beginControlFlow("if (validatable." + getter + " == null)")
                .addStatement("return false")
                .endControlFlow();
    }

    private void addSizeChecks(ValidatableField validatableField) {
        addMinSizeCheck(validatableField);
        addMaxSizeCheck(validatableField);

    }

    private void addMinSizeCheck(ValidatableField validatableField) {
        if (validatableField.getMinSize() == 0) {
            return;
        }
        addSizeCheck(
                "<", validatableField.getMinSize(), validatableField
        );
    }

    private void addMaxSizeCheck(ValidatableField validatableField) {
        if (validatableField.getMaxSize() == Integer.MAX_VALUE) {
            return;
        }
        addSizeCheck(
                ">", validatableField.getMaxSize(), validatableField
        );
    }

    private void addSizeCheck(
            String inverseOperator, int size, ValidatableField validatableField
    ) {
        final StringBuilder sizeControlFlowBuilder = new StringBuilder("if (");
        final String getter = ReflectUtils.getExistsGetterName(
                validatableClass, validatableField.getField().getName()
        ) + "()";

        if (validatableField.isNotNull() == false) {
            sizeControlFlowBuilder
                    .append("validatable.")
                    .append(getter)
                    .append(" != null && ");
        }
        sizeControlFlowBuilder
                .append("validatable.")
                .append(getter)
                .append(".")
                .append(getSizeMethod(validatableField.getField()))
                .append(" ")
                .append(inverseOperator)
                .append(" ")
                .append(size)
                .append(")");
        validateMethodBuilder.beginControlFlow(sizeControlFlowBuilder.toString())
                .addStatement("return false")
                .endControlFlow();
    }

    private String getSizeMethod(Field field) {
        for (Method method : field.getType().getMethods()) {
            if (method.getName().equals("size")
                    && ReflectUtils.isAssignableFromInt(method.getReturnType())) {
                return "size()";
            } else if (method.getName().equals("length")
                    && ReflectUtils.isAssignableFromInt(method.getReturnType())) {
                return "length()";
            }
        }
        for (Field fieldField : field.getType().getFields()) {
            if (fieldField.getName().equals("length")
                    && ReflectUtils.isAssignableFromInt(fieldField.getType())) {
                return "length";
            }
        }
        throw new IllegalStateException("Field annotated with @Size " +
                "should have public method size() or length() that return int or public field length");
    }

}
