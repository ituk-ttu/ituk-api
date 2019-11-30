package ee.ituk.api.common.validation.personal;

import ee.ituk.api.common.domain.PersonalData;
import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.PERSONAL_CODE_INCORRECT;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class HasValidPersonalCode implements ValidationRule<PersonalData> {

    @Override
    public List<ErrorMessage> apply(PersonalData person) {
        if (person.getPersonalCode().length() == 11) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(PERSONAL_CODE_INCORRECT).build());
    }
}
