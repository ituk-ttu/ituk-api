package ee.ituk.api.mentor.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String picture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
