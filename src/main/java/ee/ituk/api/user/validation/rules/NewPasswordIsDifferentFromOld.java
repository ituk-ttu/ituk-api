package ee.ituk.api.user.validation.rules;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.validation.ValidationRule;
import ee.ituk.api.user.dto.PasswordChangeDto;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.PASSWORD_UNCHANGED;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class NewPasswordIsDifferentFromOld implements ValidationRule<PasswordChangeDto> {
    @Override
    public List<ErrorMessage> apply(PasswordChangeDto dto) {
        if (!dto.getNewPassword().equals(dto.getOldPassword())) {
            return emptyList();
        }
        return singletonList(ErrorMessage.builder().code(PASSWORD_UNCHANGED).build());
    }
}
