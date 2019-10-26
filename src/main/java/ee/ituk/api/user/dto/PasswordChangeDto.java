package ee.ituk.api.user.dto;

import lombok.Getter;

@Getter
public class PasswordChangeDto {
    private String newPassword;
    private String oldPassword;
}
