# fields-validator
Annotation processor for simple objects validator – null and size checks.

## Usage
All you need to do is annotate the required validatable class and its fields.
```java
    @Validatable
    public class SomeClass {
        
        @Validate(notNull = true, minSize = 3, maxSize = 32)
        private String field;
        
        public String getField() {
            return field;
        }
        
    }
```
During the build of the project, will be generated class SomeClassValidator (original class name + 'Validator'). This class will implement interface Validator<T> and has one method - validate(T validatable). It returns a boolean variable - the result of validation. This class can be used to validate an object:
```java
    final Validator<SomeClass> validator = new SomeClassValidator();
    final boolean result = validator.validate(someClassInstance);
```
Important: when a field value can be null, but minimum and/or maximum size are specified, if field value equals null it it will be considered correct.

## Install
1. Add maven local repository to repositories in file build.gradle.
```groovy
    repositories {
        mavenCentral()
        mavenLocal()
    }
```
2. Unzip downloaded files into maven local repository. It located in ~/.m2/repository for Unix/MacOS and in C:\Users\<username>\.m2\repository for Windows.
3. Add the following dependencies to build.gradle:
```groovy
    dependencies {
        implementation 'com.ximand:fields-validator:1.0.1'
        annotationProcessor 'com.ximand:fields-validator:1.0.1'
        
        ...
    }
```
