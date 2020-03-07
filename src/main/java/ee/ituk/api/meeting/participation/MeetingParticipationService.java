package ee.ituk.api.meeting.participation;

import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.meeting.general.GeneralMeeting;
import ee.ituk.api.meeting.general.GeneralMeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.checkForErrors;
import static ee.ituk.api.common.validation.ValidationUtil.getNotFoundError;
import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class MeetingParticipationService {

    private final GeneralMeetingService generalMeetingService;
    private final MeetingParticipationRepository repository;

    private final MeetingParticipantValidator validator = new MeetingParticipantValidator();

    public List<MeetingParticipation> add(List<MeetingParticipation> participants) {
        participants.forEach(participant -> checkForErrors(validator.validateOnCreate(participant)));
        return repository.saveAll(participants);
    }

    public void delete(Long id) {
        MeetingParticipation participation = repository.findById(id).orElseThrow(
                () -> new NotFoundException(singletonList(getNotFoundError(this.getClass()))));
        repository.delete(participation);
    }

    List<MeetingParticipation> getAllParticipantsByMeeting(Long id) {
        GeneralMeeting meeting = generalMeetingService.findById(id);
        return repository.getAllByGeneralMeeting(meeting).orElseThrow(
                () -> new NotFoundException(singletonList(getNotFoundError(this.getClass())))
        );
    }

    void update(MeetingParticipation participant) {
        generalMeetingService.findById(participant.getGeneralMeeting().getId());
        repository.save(participant);
    }
}
