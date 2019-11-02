package ee.ituk.api.user.validation.rules;

import ee.ituk.api.common.validation.ValidationError;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.user.domain.User;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static ee.ituk.api.common.validation.ValidationUtil.STUDENTCODE_INCORRECT;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@AllArgsConstructor
public class HasValidStudentCode implements ValidationRule<User> {

    private final Pattern studentCodePattern = Pattern.compile("^\\d{6}[iyIY][a-zA-Z]{2}[bmdBMD]$");

    @Override
    public List<ValidationError> apply(User user) {
        if (studentCodePattern.matcher(user.getStudentCode()).matches()) {
            return emptyList();
        }
        return singletonList(ValidationError.builder().code(STUDENTCODE_INCORRECT).build());
    }
}
