package ee.ituk.api.user.dto;

import lombok.Data;

@Data
public class UserStatusDto {
    private Long id;
    private String statusName;
    private String description;
}
