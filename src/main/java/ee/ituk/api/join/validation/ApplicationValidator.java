package ee.ituk.api.join.validation;

import ee.ituk.api.common.validation.ValidationResult;
import ee.ituk.api.common.validation.Validator;
import ee.ituk.api.common.validation.personal.HasName;
import ee.ituk.api.common.validation.personal.HasValidEmail;
import ee.ituk.api.common.validation.personal.HasValidPersonalCode;
import ee.ituk.api.common.validation.personal.HasValidStudentCode;
import ee.ituk.api.join.domain.Application;

import java.util.List;

public class ApplicationValidator extends Validator {

    public ValidationResult validateOnCreate(Application application) {

        return applyRules(application, List.of(
                new HasName(),
                new HasValidStudentCode(),
                new HasValidPersonalCode(),
                new HasValidEmail()
        ));
    }
}
