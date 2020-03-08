package ee.ituk.api.join.service;

import ee.ituk.api.mentor.domain.MentorProfile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplicationsMentor {
    private final String name;
    private final Long applicationId;
    private final Long mentorId;
    private final MentorProfile mentor;
}
