package ee.ituk.api.common.validation;

import ee.ituk.api.common.exception.ValidationException;

public final class ValidationUtil {

    public static final String PASSWORD_INCORRECT = "password.incorrect";
    public static final String PASSWORD_NOT_SECURE = "password.not.secure";
    public static final String PASSWORD_UNCHANGED = "password.unchanged";

    public static final String STUDENTCODE_INCORRECT = "studentCode.incorrect";

    public static final String NAME_MISSING = "name.missing";

    private ValidationUtil() {
        //not instance
    }

    public static void checkForErrors(ValidationResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
    }
}
