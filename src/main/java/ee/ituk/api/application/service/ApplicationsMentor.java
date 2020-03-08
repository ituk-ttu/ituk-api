package ee.ituk.api.application.service;

import ee.ituk.api.mentor.dto.MentorProfileDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplicationsMentor {
    private final String name;
    private final Long applicationId;
    private final MentorProfileDto mentor;
}
