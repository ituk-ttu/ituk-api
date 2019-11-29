package ee.ituk.api.join.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationMentorRequest {
    private Long applicationId;
    private Long mentorId;
}
