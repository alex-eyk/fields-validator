package com.ximand;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
public class ValidatorProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_15;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>() {{
            add(Validatable.class.getCanonicalName());
            add(Validate.class.getCanonicalName());
        }};
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        final Set<? extends Element> annotatedElements = roundEnv
                .getElementsAnnotatedWith(Validatable.class);
        for (Element element : annotatedElements) {
            try {
                final TypeElement typeElement = (TypeElement) element;
                final Class<?> class_ = getClass(typeElement);
                final List<ValidatableField> validatableFields = new ArrayList<>();
                for (Field field : class_.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Validate.class) == false) {
                        continue;
                    }
                    final Validate validate = field.getAnnotation(Validate.class);
                    validatableFields.add(
                            new ValidatableField(field)
                                    .setNotNull(validate.notNull())
                                    .setMinSize(validate.minSize())
                                    .setMaxSize(validate.maxSize())
                    );
                }
                final ValidatorCodeGenerator codeGenerator = new ValidatorCodeGenerator(validatableFields);
                codeGenerator.generate(class_);
            } catch (ClassCastException e) {
                throw new IllegalStateException("Only class can be annotated with @Validatable", e);
            } catch (IOException e) {
                throw new IllegalStateException("Unable to create java files", e);
            }
        }
        return false;
    }

    private Class<?> getClass(TypeElement element) {
        try {
            return Class.forName(element.toString());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to find class. Element: " + element.toString(), e);
        }
    }

}
