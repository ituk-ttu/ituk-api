package ee.ituk.api.user.dto;

import lombok.Getter;

@Getter
public class NewPasswordDto {
    private String code;
    private String password;
}
