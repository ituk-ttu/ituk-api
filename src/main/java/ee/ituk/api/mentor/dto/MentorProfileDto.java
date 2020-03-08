package ee.ituk.api.mentor.dto;

import ee.ituk.api.user.dto.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MentorProfileDto {
    private Long id;
    private UserDto user;
    private String text;
    private String gif;
    private String quote;
    private boolean enabled;
    private String picture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
