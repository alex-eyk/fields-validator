package com.ximand;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.ximand.impl.spec.password.PasswordSpec;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

class ValidateMethodBuilder {

    private static final String VALIDATE_METHOD_NAME = "validate";
    private static final String VALIDATE_METHOD_PARAM = "validatable";
    private static final String[] SIZE_MEMBERS = new String[]{
            "length", "length()", "size()"
    };

    final ClassName emailValidator = ClassName.get("com.ximand.impl", "EmailValidator");
    final ClassName emailFactory = ClassName.get("com.ximand.impl.spec.email", "EmailSpecificationFactory");
    final ClassName emailSpecs = ClassName.get("com.ximand.impl.spec.email", "EmailSpec");
    final ClassName passwordValidator = ClassName.get("com.ximand.impl", "PasswordValidator");
    final ClassName passwordFactory = ClassName.get("com.ximand.impl.spec.password", "PasswordSpecificationFactory");
    final ClassName passwordSpec = ClassName.get("com.ximand.impl.spec.password", "PasswordSpec");
    final ClassName customSpec = ClassName.get("com.ximand.impl.spec.password", "CustomPasswordSpecification");

    private final MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(VALIDATE_METHOD_NAME);
    private final Elements elementUtils;

    private int variables = 0;
    private String getter;

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

    void addConditionsChecks(ValidateField validateField, String getter) {
        this.getter = getter;

        addNotNullChecks(validateField);
        final int minSize = validateField.getMinSize();
        if (minSize != ValidateField.DEF_MIN_SIZE) {
            addSizeCheck("<", minSize, validateField.isNotNull(), validateField.getElement());
        }
        final int maxSize = validateField.getMaxSize();
        if (maxSize != ValidateField.DEF_MAX_SIZE) {
            addSizeCheck(">", maxSize, validateField.isNotNull(), validateField.getElement());
        }
        if (validateField instanceof RegexValidateField) {
            addRegexCheck((RegexValidateField) validateField);
        } else if (validateField instanceof EmailValidateField) {
            addEmailCheck((EmailValidateField) validateField);
        } else if (validateField instanceof PasswordValidateField) {
            addPasswordCheck((PasswordValidateField) validateField);
        }
    }

    private void addNotNullChecks(ValidateField validateField) {
        if (validateField.isNotNull() == false) {
            return;
        }
        methodBuilder
                .beginControlFlow("if (validatable." + getter + " == null)")
                .addStatement("return false")
                .endControlFlow();
    }

    private void addSizeCheck(
            String operator, int size, boolean notNull, Element validElement
    ) {
        final StringFormatBuilder sizeControlFlowBuilder = new StringFormatBuilder("if (");
        final String sizeMethod = findSizeMethod(validElement);
        if (notNull == false) {
            sizeControlFlowBuilder.appendf("validatable.%s != null && ", getter);
        }
        sizeControlFlowBuilder
                .appendf("validatable.%s.%s %s %d", getter, sizeMethod, operator, size)
                .append(")");
        methodBuilder.beginControlFlow(sizeControlFlowBuilder.toString())
                .addStatement("return false")
                .endControlFlow();
    }

    private String findSizeMethod(Element field) {
        final String method = ReflectUtils.findMethod(elementUtils, field, SIZE_MEMBERS);
        if (method != null) {
            return method;
        } else {
            throw new IllegalStateException("Object (type: " + field + ") that limited in size " +
                    "should contains size method (size(), length() or length public field)");
        }
    }

    void addRegexCheck(RegexValidateField field) {
        final String regex = field.getRegex();
        methodBuilder
                .beginControlFlow("if (validatable != null && !validatable.$L.matches($S))", getter, regex)
                .addStatement("return false")
                .endControlFlow();
    }

    void addEmailCheck(EmailValidateField field) {
        final String specName = field.getEmailSpec().name();
        addValidatorCheck(emailValidator, emailFactory, emailSpecs, specName);
    }

    void addPasswordCheck(PasswordValidateField field) {
        if (field.getPasswordSpec() == PasswordSpec.CUSTOM) {
            addCustomPasswordCheck(field);
        } else {
            final String specName = field.getPasswordSpec().name();
            addValidatorCheck(passwordValidator, passwordFactory, passwordSpec, specName);
        }
    }

    private void addValidatorCheck(TypeName validator, TypeName factory, TypeName spec, String specName) {
        this.variables += 1;
        methodBuilder.addCode(
                CodeBlock.builder()
                        .add("final $T var$L = new $T($T.createBySpec($T.$L));\n",
                                validator, variables, validator, factory, spec, specName)
                        .build()
        );
        methodBuilder.beginControlFlow("if (validatable != null && !var$L.validate(validatable.$L))", variables, getter)
                .addStatement("return false")
                .endControlFlow();
    }

    private void addCustomPasswordCheck(PasswordValidateField field) {
        this.variables += 1;
        methodBuilder.addCode(CodeBlock.builder()
                .add(
                        "$T var$L = new $T(new $T()" +
                                ".setMinLength($L)" +
                                ".setMaxLength($L)" +
                                ".setLowerCaseChars($L)" +
                                ".setUpperCaseChars($L)" +
                                ".setDigitChars($L)" +
                                ".setSpecialChars($L)" +
                                ".setAllowedSpecialChars($S)" +
                                ");\n",
                        passwordValidator, variables, passwordValidator, customSpec, field.getMinLength(),
                        field.getMaxLength(), field.getLowerCase(), field.getUpperCase(),
                        field.getDigits(), field.getSpecial(), field.getAllowedSpecial()
                )
                .add("if (!var$L.validate(validatable.$L))", variables, getter)
                .build());
    }

    MethodSpec build() {
        return methodBuilder
                .addStatement("return true")
                .build();
    }

}
