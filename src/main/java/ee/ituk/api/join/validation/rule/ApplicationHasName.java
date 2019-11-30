package ee.ituk.api.join.validation.rule;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.join.domain.Application;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.NAME_MISSING;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class ApplicationHasName implements ValidationRule<Application> {

    @Override
    public List<ErrorMessage> apply(Application application) {
        if (isNotBlank(application.getFirstName()) || isNotBlank(application.getLastName())) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(NAME_MISSING).build());
    }
}
