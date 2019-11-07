package ee.ituk.api.mentor.dto;

import ee.ituk.api.user.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MentorProfileDto {
    private Long id;
    private User user;
    private String text;
    private String gif;
    private String quote;
    private boolean enabled;
    private String picture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
