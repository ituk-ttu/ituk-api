package ee.ituk.api.common.validation;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.exception.ValidationException;

public final class ValidationUtil {

    public static final String PASSWORD_INCORRECT = "password.incorrect";
    public static final String PASSWORD_NOT_SECURE = "password.not.secure";
    public static final String PASSWORD_UNCHANGED = "password.unchanged";

    public static final String STUDENTCODE_INCORRECT = "studentCode.incorrect";

    public static final String NAME_MISSING = "name.missing";

    public static final String PROJECT_MISSING_REQUIRED_FIELDS = "project.missing.required.fields";
    public static final String PROJECT_BUDGET_MISSING_REQUIRED_FIELDS = "project.budget.missing.required.fields";
    public static final String PROJECT_BUDGET_ROW_MISSING_REQUIRED_FIELDS = "project.budget.row.missing.required.fields";
    public static final String PROJECT_BUDGET_ROW_VALUES_NOT_VALID = "project.budget.row.values.not.valid";
    public static final String PROJECT_MEMBER_NOT_VALID = "project.member.not.valid";
    public static final String PROJECT_SUMMARY_MISSING_REQUIRED_FIELDS = "project.summary.missing.required.fields";

    public static final String RESOURCE_URL_MISSING = "url.missing";
    public static final String RESOURCE_AUTHOR_MISSING = "author.missing";

    public static final String MEETING_ID_MISMATCH = "meeting.id.mismatch";

    public static final String PARTICIPANT_ID_MISMATCH = "participant.id.mismatch";

    private ValidationUtil() {
        //not instance
    }

    public static void checkForErrors(ValidationResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
    }

    public static ErrorMessage getNotFoundError(Class aClass) {
        String objectName = aClass.getSimpleName().toLowerCase();
        return new ErrorMessage(objectName + ".not.found", objectName + " not found");
    }
}
