package ee.ituk.api.meeting.general;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.common.exception.ValidationException;
import ee.ituk.api.common.validation.ValidationUtil;
import ee.ituk.api.meeting.agenda.MeetingAgenda;
import ee.ituk.api.meeting.agenda.MeetingAgendaItemRepository;
import ee.ituk.api.meeting.agenda.MeetingAgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static ee.ituk.api.common.validation.ValidationUtil.*;

@Service
@RequiredArgsConstructor
public class GeneralMeetingService {

    private final GeneralMeetingsRepository meetingsRepository;
    private final MeetingAgendaItemRepository meetingAgendaItemRepository;
    private final MeetingAgendaRepository meetingAgendaRepository;

    private final GeneralMeetingValidator meetingValidator = new GeneralMeetingValidator();

    public GeneralMeeting create(GeneralMeeting meeting) {
        checkForErrors(meetingValidator.validateOnCreate(meeting));
        if (meeting.getMeetingAgenda() != null) {
            MeetingAgenda meetingAgenda = meetingAgendaRepository.save(meeting.getMeetingAgenda());
            meeting.getMeetingAgenda().getItems().forEach(item -> item.setMeetingAgenda(meetingAgenda));
            meetingAgendaItemRepository.saveAll(meeting.getMeetingAgenda().getItems());
            meeting.setMeetingAgenda(meetingAgenda);
        }

        return meetingsRepository.save(meeting);
    }

    public void delete(Long id) {
        GeneralMeeting meeting = meetingsRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Collections.singletonList(ValidationUtil.getNotFoundError(GeneralMeeting.class))));
        meetingsRepository.delete(meeting);
    }

    public GeneralMeeting findById(Long id) {
        return meetingsRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Collections.singletonList(getNotFoundError(GeneralMeeting.class)))
        );
    }

    List<GeneralMeeting> getAll() {
        List<GeneralMeeting> meetings = meetingsRepository.findAll();
        meetings.sort(Comparator.comparing(GeneralMeeting::getDate).reversed());
        return meetings;
    }

    GeneralMeeting update(Long id, GeneralMeeting meeting) {
        validateGeneralMeeting(id, meeting);
        GeneralMeeting meetingWithUpdatedAgenda = updateMeetingsAgenda(meeting);
        return meetingsRepository.save(meetingWithUpdatedAgenda);
    }

    void removeAgendaItem(Long agendaItemId, Long meetingId) {
        // validate the meeting actually exists
        meetingsRepository.findById(meetingId).orElseThrow(
                () -> new NotFoundException(Collections.singletonList(ValidationUtil.getNotFoundError(GeneralMeeting.class))));

        meetingAgendaItemRepository.deleteById(agendaItemId);
    }

    private GeneralMeeting updateMeetingsAgenda(GeneralMeeting meeting) {
        MeetingAgenda agenda = meeting.getMeetingAgenda();
        if (Objects.nonNull(agenda)) {
            MeetingAgenda savedAgenda = meetingAgendaRepository.save(agenda);
            meetingAgendaItemRepository.saveAll(savedAgenda.getItems());
            meeting.setMeetingAgenda(savedAgenda);
        }

        return meeting;
    }

    private void validateGeneralMeeting(Long id, GeneralMeeting meeting) {
        meetingsRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Collections.singletonList(ValidationUtil.getNotFoundError(GeneralMeeting.class))));
        if (!id.equals(meeting.getId())) {
            throw new ValidationException(ErrorMessage.builder().code(MEETING_ID_MISMATCH).build());
        }
    }

    public GeneralMeeting getById(Long id) {
        return meetingsRepository.getOne(id);
    }
}
