package ee.ituk.api.user.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String cardNumber;
    private String studentCode;
    private UserStatusDto status;
    private String curriculum;
    private String iban;
    private String role;
    private boolean archived;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
