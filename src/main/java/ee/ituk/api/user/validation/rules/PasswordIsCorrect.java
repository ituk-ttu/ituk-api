package ee.ituk.api.user.validation.rules;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.BasicValidationRule;
import ee.ituk.api.user.domain.User;
import lombok.AllArgsConstructor;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.PASSWORD_INCORRECT;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@AllArgsConstructor
public class PasswordIsCorrect implements BasicValidationRule {

    private User user;
    private String password;

    @Override
    public List<ErrorMessage> apply() {
        if (user.getPassword().equals(password)) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(PASSWORD_INCORRECT).build());
    }
}
