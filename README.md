# fields-validator
Annotation processor for objects validation – null and size checks, regex, password and email checks.

## Validate
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

### Regex
You can also check a string for a match to regular expression:
```java
    @Regex(".*")
    @Validate(notNull = true, minSize = 3, maxSize = 32)
    private String field;
```
Addition: parameters passed to @Validate can be any in this and in further cases.

### Email
To check email address, mark the field with an annotation Email.
```java
    @Email
    @Validate(notNull = true)
    private String email;
```
There are 3 ways to validate an email:
* Simple — Validation using a simple small regular expression, that can validate almost all email addresses, but does not correspond to the standard. Can recognize some existing email addresses as invalid, however the probability is very low.
* HTML — Validation using regular expression, that correspond to W3C HTML standard. This standard was created because of RFC standard is very redundancy. Regular expression is larger than from Simple.
* RFC — Validation using very large regular expression, that correspond to RFC standard. Works with all email addresses, but very redundancy.
Default type — HTML. It can be changed:
```java
    @Email(EmailSpec.SIMPLE)
    @Validate
    private String email;
```

### Password
To check password, mark the field with an annotation Password.
```java
    @Password
    @Validate
    private String email;
```
There are 4 ways to validate a password:
* Simple — at least 8 characters, of which at least 1 character and 1 digits.
* Default — at least 8 characters, of which at least 1 upper case characters, 1 lower case characters, 1 digits and 1 special symbols: ('_', '.', '(', ')', '[', ']', '|', '{', '}', '+', '@', '^', '$', '!', '/', '-', '%', '*', '#', '?', '&', '\').
* Strong — at least 10 characters, of which at least 2 upper case characters, 2 lower case characters, 2 digits and 2 special symbols (as in the Default)
* Custom — If prepared specifications are not suitable, then you can use custom specification, setting the required number of characters yourself.
Example of custom password validation:
```java
    @Password(value = PasswordSpec.CUSTOM, minLength = 10, maxLength = 60, lowerCase = 2, upperCase = 2, digits = 3, special = 1, allowedSpecial = "-+!$#")
    @Validate
    private String email;
```
It is not necessary to provide values for each parameter.

### Without generate
The ability to validate email and password can be used without generating a code.
```java
    final Validator<String> validator = new PasswordValidator(new DefaultPasswordSpecification());
    final boolean result = validator.validate(password);
```
    
## Install
1. Download [latest release](https://github.com/Ximand931/fields-validator/releases/tag/v2.0.0 "fields-validator v2.0.0")
2. Unzip downloaded file into maven local repository. It located in ~/.m2/repository for Unix/MacOS and in C:\Users\<username>\.m2\repository for Windows.
3. Add maven local repository to repositories in file build.gradle.
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
