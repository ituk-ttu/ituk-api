package ee.ituk.api.meeting;

import ee.ituk.api.meeting.domain.GeneralMeeting;
import ee.ituk.api.meeting.validation.GeneralMeetingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.checkForErrors;

@Service
@RequiredArgsConstructor
public class GeneralMeetingService {

    private final GeneralMeetingsRepository meetingsRepository;
    private final GeneralMeetingValidator meetingValidator = new GeneralMeetingValidator();

    public List<GeneralMeeting> getAll() {
        return meetingsRepository.findAll();
    }

    public GeneralMeeting create(GeneralMeeting meeting) {
        checkForErrors(meetingValidator.validateOnCreate(meeting));
        return meetingsRepository.save(meeting);
    }
}
