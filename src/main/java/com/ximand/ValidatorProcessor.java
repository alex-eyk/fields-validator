package com.ximand;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@AutoService(Processor.class)
public final class ValidatorProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Filer filer;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>() {{
            add(Validatable.class.getCanonicalName());
            add(Validate.class.getCanonicalName());
        }};
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elementUtils = processingEnv.getElementUtils();
        this.filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        final Set<? extends Element> annotatedTypes = roundEnv
                .getElementsAnnotatedWith(Validatable.class);
        for (Element annotatedType : annotatedTypes) {
            try {
                final TypeElement typeElement = (TypeElement) annotatedType;
                final Set<ValidateField> validatableFields = new HashSet<>();
                for (Element enclosedElement : elementUtils.getAllMembers(typeElement)) {
                    final Validate validate = enclosedElement.getAnnotation(Validate.class);
                    if (validate == null) {
                        continue;
                    }
                    validatableFields.add(
                            ValidateFieldFactory.createByValidate(enclosedElement, validate)
                    );
                }
                final ValidatorCodeGenerator codeGenerator = new ValidatorCodeGenerator(elementUtils, filer);
                codeGenerator.generate(typeElement, validatableFields);
            } catch (ClassCastException e) {
                throw new IllegalStateException("Only class can be annotated with @Validatable", e);
            } catch (IOException e) {
                throw new IllegalStateException("Unable to create java files", e);
            }
        }
        return true;
    }

}
