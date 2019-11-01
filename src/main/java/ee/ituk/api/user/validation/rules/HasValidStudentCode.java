package ee.ituk.api.user.validation.rules;

import ee.ituk.api.common.validation.ValidationError;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.user.domain.User;

import java.util.List;

public class HasValidStudentCode implements ValidationRule<User> {

    @Override
    public List<ValidationError> apply(User user) {
        //TODO
        return null;
    }
}
