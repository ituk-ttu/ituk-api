package ee.ituk.api.join.validation.rule;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.join.domain.Application;

import java.util.List;
import java.util.regex.Pattern;

import static ee.ituk.api.common.validation.ValidationUtil.NAME_MISSING;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class ApplicationHasStudentCode implements ValidationRule<Application> {

    private final Pattern studentCodePattern = Pattern.compile("^\\d{6}[IYT][A-Z]{2}[BMD]?$");

    @Override
    public List<ErrorMessage> apply(Application application) {
        if (isNotBlank(application.getStudentCode())) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(NAME_MISSING).build());
    }

}
