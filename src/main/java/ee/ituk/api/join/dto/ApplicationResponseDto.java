package ee.ituk.api.join.dto;

import ee.ituk.api.user.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String personalCode;
    private String email;
    private String studentCode;
    private String curriculum;
    private String mentorSelectionCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long processedById;
    private User mentor;
}
