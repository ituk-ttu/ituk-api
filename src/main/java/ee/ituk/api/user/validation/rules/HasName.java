package ee.ituk.api.user.validation.rules;

import ee.ituk.api.common.validation.ValidationError;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.user.domain.User;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.NAME_MISSING;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class HasName implements ValidationRule<User> {
    @Override
    public List<ValidationError> apply(User user) {
        if (isNotBlank(user.getFirstName()) || isNotBlank(user.getLastName())) {
            return emptyList();
        }
        return singletonList(ValidationError.builder().code(NAME_MISSING).build());
    }
}
