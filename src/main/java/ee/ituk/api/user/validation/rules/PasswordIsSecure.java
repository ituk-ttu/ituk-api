package ee.ituk.api.user.validation.rules;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.BasicValidationRule;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static ee.ituk.api.common.validation.ValidationUtil.PASSWORD_NOT_SECURE;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.StringUtils.isBlank;

@RequiredArgsConstructor
public class PasswordIsSecure implements BasicValidationRule {

    private static final Predicate<String> PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z]).+$").asMatchPredicate();

    private final String password;

    @Override
    public List<ErrorMessage> apply() {
        if (isBlank(password) || (password.length() > 7 && PASSWORD_PATTERN.test(password))) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(PASSWORD_NOT_SECURE).build());
    }
}
