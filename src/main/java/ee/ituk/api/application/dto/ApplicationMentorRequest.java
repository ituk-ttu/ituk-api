package ee.ituk.api.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationMentorRequest {
    private Long applicationId;
    private Long mentorId;
}
