package ee.ituk.api.user.validation;

import ee.ituk.api.common.validation.ValidationResult;
import ee.ituk.api.common.validation.Validator;
import ee.ituk.api.common.validation.personal.HasName;
import ee.ituk.api.common.validation.personal.HasValidEmail;
import ee.ituk.api.common.validation.personal.HasValidPersonalCode;
import ee.ituk.api.common.validation.personal.HasValidStudentCode;
import ee.ituk.api.user.domain.User;
import ee.ituk.api.user.dto.PasswordChangeDto;
import ee.ituk.api.user.validation.rules.NewPasswordIsDifferentFromOld;
import ee.ituk.api.user.validation.rules.PasswordIsSecure;

import java.util.Arrays;

import static java.util.Collections.singletonList;

public class UserValidator extends Validator {

    public ValidationResult validateOnCreate(User user) {
        // TODO: add missing validation rules
        ValidationResult validationResult = applyRules(singletonList(
                new PasswordIsSecure(user.getPassword())
        ));
        validationResult.add(applyRules(user, Arrays.asList(
                new HasName(),
                new HasValidStudentCode(),
                new HasValidPersonalCode(),
                new HasValidEmail()
        )));
        return validationResult;
    }

    public ValidationResult validatePasswordChange(PasswordChangeDto dto) {
        ValidationResult validationResult = applyRule(new PasswordIsSecure(dto.getNewPassword()));
        return validationResult.hasErrors() ? validationResult : applyRules(dto, singletonList(
                new NewPasswordIsDifferentFromOld()
        ));
    }

    public ValidationResult validatePersonalCode(User user) {
        return applyRule(user, new HasValidPersonalCode());
    }
}
