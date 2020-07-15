package ee.ituk.api.user.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserBirthdayDto {
    private final String fullName;
    private final LocalDate birthday;
}
