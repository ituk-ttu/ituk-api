package ee.ituk.api.user.validation.rules;

import ee.ituk.api.common.validation.BasicValidationRule;
import ee.ituk.api.common.validation.ValidationError;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.regex.Pattern;

import static ee.ituk.api.common.validation.ValidationUtil.PASSWORD_NOT_SECURE;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@AllArgsConstructor
public class PasswordIsSecure implements BasicValidationRule {

    private final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).+$");

    private String password;

    @Override
    public List<ValidationError> apply() {
        if (password.length() > 9 && passwordPattern.matcher(password).matches()) {
            return emptyList();
        }
        return singletonList(ValidationError.builder().code(PASSWORD_NOT_SECURE).build());
    }
}
