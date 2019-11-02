package ee.ituk.api.user.validation.rules;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.user.domain.User;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.regex.Pattern;

import static ee.ituk.api.common.validation.ValidationUtil.STUDENTCODE_INCORRECT;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@AllArgsConstructor
public class HasValidStudentCode implements ValidationRule<User> {

    private final Pattern studentCodePattern = Pattern.compile("^\\d{6}[IYT][A-Z]{2}[BMD]?$");

    @Override
    public List<ErrorMessage> apply(User user) {
        if (studentCodePattern.matcher(user.getStudentCode().toUpperCase()).matches()) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(STUDENTCODE_INCORRECT).build());
    }
}
