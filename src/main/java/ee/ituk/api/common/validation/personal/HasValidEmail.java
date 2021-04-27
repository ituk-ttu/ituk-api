package ee.ituk.api.common.validation.personal;

import ee.ituk.api.common.domain.PersonalData;
import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.EMAIL_INCORRECT;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class HasValidEmail implements ValidationRule<PersonalData> {

    @Override
    public List<ErrorMessage> apply(PersonalData person) {
        if (EmailValidator.getInstance(true).isValid(person.getEmail())) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(EMAIL_INCORRECT).build());
    }
}
