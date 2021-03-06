package com.ximand;

/**
 * Responsible for validating required fields of some object. The validatable object
 * must be the instance of some validatable class annotated with {@code @Validatable}.
 * All validator subclasses will be generated automatically during project build. The
 * subclass name generate based on the name of validatable type and suffix 'Validator'.
 * <p>
 * All fields in validatable class that need to be checked should be <b>annotated</b> with
 * {@code @Validate} and <b>have a getter</b>. If field not annotated with {@code @Validate},
 * this field will be ignored during generation validators.
 *
 * @param <T> Validatable type annotated with {@code @Validatable}
 * @author Aleksej Kiselev
 */
@SuppressWarnings("unused")
public interface Validator<T> {

    /**
     * The method is generated automatically. All fields annotated with {@code @Validate}
     * are checked for validity in it as follows:
     * <p>
     * For not {@code null} fields will generate next code:
     * <pre>
     * {@code
     *     if (validatable.getField() == null) {
     *         return false;
     *     }
     * }
     * </pre>
     * <p>
     * If field value should be limited in size and field can not be {@code null}, in
     * addition to the previous will generate next code (for example, {@code minSize = 10}):
     * <pre>
     * {@code
     *     if (validatable.getField().length() {@literal <} 10) {
     *         return false;
     *     }
     * }
     * </pre>
     * The same code will be generated for the {@code maxValue}. Difference in sign of
     * inequality.
     * <p>
     * <b>An important nuance.</b> If field can be {@code null}, but limited in size, will
     * generate next code:
     * <pre>
     * {@code
     *     if (validatable.getField() != null {@literal &&} validatable.getField().length() {@literal <} 10) {
     *         return false;
     *     }
     * }
     * </pre>
     * That is, if the field is limited in size, but has null reference {@code null}, <b>it
     * will be considered valid</b>
     * <p>
     * Annotated with {@code @Validate} fields also can be annotated with {@code @Regex}. In
     * this case fields, will be validate by regular expression (if {@code field != null}).
     * Will also validate conditions from {@code @Validate} annotation.
     * If field annotated with {@code @Regex(".*")} and {@code @Validate} will generate next
     * code:
     * <pre>
     * {@code
     *     if (validatable != null {@literal &&} !validatable.getRegex().matches(".*")) {
     *         return false;
     *     }
     * }
     * </pre>
     * <p>
     * In addition to {@code @Regex} annotation exists {@code @Email} and {@code @Password}
     * annotations, that can use for validation email address and password.
     * For email exists 3 ready-to-use implementations {@link com.ximand.impl.spec.email.EmailSpec}:
     * simple specification, HTML and RFC.
     * For password exists 3 ready-to-use implementations {@link com.ximand.impl.spec.password.PasswordSpec}:
     * simple specification, default, strong and custom.
     *
     * @param validatable The object to be checked for validity
     * @return The result of checking the object for validity
     */
    boolean validate(T validatable);

}
