# fields-validator
Процессор аннотаций для валидации объектов, предназначен для проверок на null, проверки размеров строк и массивов, проверки на соответсвие регулярным выражениям, корректности адреса эл. почты и пароля.

## Валидация
Пометьте класс, объекты которого вы собиратесь проверять, аннотацией @Validatable:
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
Во время сборки проекта будет сгенерирован класс SomeClassValidator (Имя строится по правилу: оригинальное название + 'Validator'). Созданный класс реализует интерфейс Validator<T> и имеет один метод — validate(T validatable). Возвращаемое значение - boolean - результат валидации.
После этого вы можете проверить объект при помощи сгенерированного класса:
```java
    final Validator<SomeClass> validator = new SomeClassValidator();
    final boolean result = validator.validate(someClassInstance);
```
Небольшое замечание: если для поля задано хотя бы одно условие, и при этом оно ссылается на null, поле будет считаться корректным (конечно, если оно может принимать значение null).

### Regex
Также можно проверить строку на соответствие регулярному выражению:
```java
    @Regex(".*")
    @Validate(notNull = true, minSize = 3, maxSize = 32)
    private String field;
```

### Email
```java
    @Email
    @Validate(notNull = true)
    private String email;
```
Существует три способа проверки адреса эл. почты:
* RFC — проверка используя 'тяжелое' регулярное выражение, соответсвующее стандарту RFC. Будет работать корректно со всеми адресами.
* HTML — проверка используя регулярное выражение, соответсвующее стандарту W3C HTML. Выражение в данном случае гораздо 'легче', чем у стандарта RFC.
* Simple — проверка используя простое и совсем небольшое регулярное выражение, которое может корректно проверить почти все адреса эл. почты.
Значение по умолчанию — HTML, но вы можете изменить его:
```java
    @Email(EmailSpec.SIMPLE)
    @Validate
    private String email;
```

### Пароль
```java
    @Password
    @Validate
    private String email;
```
Существует 4 способа валидации пароля:
* Simple - минимум 8 символов, 1 буква and 1 цифра.
* Default — минимум 8 символов, 1 заглавная буква, 1 строчная буква, 1 цифра и 1 спец. символ: ('_', '.', '(', ')', '[', ']', '|', '{', '}', '+', '@', '^', '$', '!', '/', '-', '%', '*', '#', '?', '&', '\').
* Strong — минимум 10 символов, 2 заглавных буквы, 2 строчных буквы, 2 цифры и 2 спец. символа (как в Default)
* Custom — можно указать любые значения для всех параметров
Пример для кастомной валидации:
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
Не обязательно указывать все параметры.

### Без генерации
Проверку email и паролей можно произвести альтернативным путем без генерации кода:
```java
    final Validator<String> validator = new PasswordValidator(new DefaultPasswordSpecification());
    final boolean result = validator.validate(password);
```
    
## Установка
1. Скачать [последний релиз](https://github.com/Ximand931/fields-validator/releases/tag/v2.0.0 "fields-validator v2.0.0")
2. Разархивировать скачанный файл локальный maven-репозиторий. Он расположен в ~/.m2/repository в Unix/macOS и в C:\Users\<username>\.m2\repository в Windows.
3. Добавить локальный maven-репозиторий в build.gradle:
```groovy
    repositories {
        mavenCentral()
        mavenLocal()
    }
```
4. Добавить следующие зависимости в build.gradle:
```groovy
    dependencies {
        implementation 'com.ximand:fields-validator:2.0.0'
        annotationProcessor 'com.ximand:fields-validator:2.0.0'
        
        ...
    }
```
