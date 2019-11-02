package ee.ituk.api.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String cardNumber;
    private String studentCode;
    private String idCode;
    private UserStatusDto status;
    private String curriculum;
    private String iban;
    private String role;
    private boolean archived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
