package ee.ituk.api.application.service;

import ee.ituk.api.mentor.dto.MentorProfileDto;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class ApplicationsMentor {
    @NonNull String name;
    @NonNull Long applicationId;
    MentorProfileDto mentor;
}
