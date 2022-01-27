# fields-validator
Annotation processor for objects validation intended for null and size checks, regex, password, and email checks.

You can read this in [Russian](README.ru.md).

## Validate
All you need to do is annotate the required validatable class and its fields:
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
During the project build, will be generated class SomeClassValidator (The name built according to the following rule: original class name + 'Validator'). This class will implement interface Validator<T> and has one method - validate(T validatable). Its return type - boolean - is the result of validation.
You can validate an object with the appropriate generated class:
```java
    final Validator<SomeClass> validator = new SomeClassValidator();
    final boolean result = validator.validate(someClassInstance);
```
It is worth clarifying that when at least one of the size limits or another condition is specified, and the field refers to null, it will be considered correct, provided that it can refer to null.

### Regex
You can also check a string for a match to the regular expression:
```java
    @Regex(".*")
    @Validate(notNull = true, minSize = 3, maxSize = 32)
    private String field;
```

### Email
To check the email address, mark the field with an annotation Email:
```java
    @Email
    @Validate(notNull = true)
    private String email;
```
There are three ways to validate an email:
* RFC — validation using 'heavy' regular expression corresponds to RFC standard. Will works correctly with all email addresses but is very redundant.
Default type — HTML.
* HTML — validation using a regular expression corresponds to the W3C HTML standard. The regular expression is much less than from RFC.
* Simple — validation using a simple small regular expression that can validate almost all email addresses but does not correspond to the standard. Can recognize some existing email addresses as invalid, but the probability is very low.
Default type — HTML, but you can change it in the following way:
```java
    @Email(EmailSpec.SIMPLE)
    @Validate
    private String email;
```

### Password
To check the password, mark the field with an annotation Password.
```java
    @Password
    @Validate
    private String email;
```
There are four ways to validate a password:
* Simple - at least 8 characters, 1 letter and 1 digits.
* Default — at least 8 characters, of which at least 1 upper case letter, 1 lower case letter, 1 digits and 1 special symbols: ('_', '.', '(', ')', '[', ']', '|', '{', '}', '+', '@', '^', '$', '!', '/', '-', '%', '*', '#', '?', '&', '\').
* Strong — at least 10 characters, of which at least 2 upper case letter, 2 lower case letter, 2 digits and 2 special symbols (as in the Default)
* Custom — if prepared specifications are not suitable, then you can use custom specification, setting the required number of characters yourself.
Example of custom password validation:
```java
    @Password(
        value = PasswordSpec.CUSTOM, 
        minLength = 10, 
        maxLength = 60, 
        lowerCase = 2, 
        upperCase = 2, 
        digits = 3, 
        special = 1, 
        allowedSpecial = "-+!$#"
    )
    @Validate
    private String email;
```
It is not necessary to provide values for each parameter.

### Without generate
There is the ability to validate email and passwords without generating a code:
```java
    final Validator<String> validator = new PasswordValidator(new DefaultPasswordSpecification());
    final boolean result = validator.validate(password);
```
    
## Install
1. Download [latest release](https://github.com/Ximand931/fields-validator/releases/tag/v2.0.0 "fields-validator v2.0.0")
2. Unzip the downloaded file into the maven local repository. It located in ~/.m2/repository for Unix/macOS and in C:\Users\<username>\.m2\repository for Windows.
3. Add your maven local repository to repositories in file build.gradle:
```groovy
    repositories {
        mavenCentral()
        mavenLocal()
    }
```
4. Add the following dependencies to build.gradle:
```groovy
    dependencies {
        implementation 'com.ximand:fields-validator:2.0.0'
        annotationProcessor 'com.ximand:fields-validator:2.0.0'
        
        ...
    }
```
