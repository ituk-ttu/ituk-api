package ee.ituk.api.common.validation.personal;

import ee.ituk.api.common.domain.PersonalData;
import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.NAME_MISSING;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class HasName implements ValidationRule<PersonalData> {
    @Override
    public List<ErrorMessage> apply(PersonalData user) {
        if (isNotBlank(user.getFirstName()) || isNotBlank(user.getLastName())) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(NAME_MISSING).build());
    }
}
