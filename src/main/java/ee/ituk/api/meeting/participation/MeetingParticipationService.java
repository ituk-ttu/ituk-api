package ee.ituk.api.meeting.participation;

import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.meeting.general.GeneralMeeting;
import ee.ituk.api.meeting.general.GeneralMeetingService;
import ee.ituk.api.user.UserMapper;
import ee.ituk.api.user.UserService;
import ee.ituk.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ee.ituk.api.common.validation.ValidationUtil.checkForErrors;
import static ee.ituk.api.common.validation.ValidationUtil.getNotFoundError;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class MeetingParticipationService {

    private final GeneralMeetingService generalMeetingService;
    private final UserService userService;
    private final MeetingParticipationRepository repository;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final MeetingParticipationMapper participantMapper = Mappers.getMapper(MeetingParticipationMapper.class);
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

    List<MeetingParticipationDto> getAllParticipantsByMeeting(Long id) {
        GeneralMeeting meeting = generalMeetingService.findById(id);
        List<UserDto> users = userMapper.usersToDto(userService.findAll().stream()
                .filter(user -> !user.isArchived())
                .collect(Collectors.toList()));
        List<MeetingParticipationDto> participants = participantMapper.entitiesToDtos(repository.getAllByGeneralMeeting(meeting).orElse(emptyList()));
        List<Long> participantUserIds = participants.stream()
                .filter(MeetingParticipationDto::isParticipated)
                .map(p -> p.getUser().getId()).collect(Collectors.toList());

        List<MeetingParticipationDto> mappedParticipants = users.stream()
                .map(mapAllUserParticipations(participantUserIds, id))
                .sorted(Comparator.comparing(MeetingParticipationDto::isParticipated).reversed()) // first true then false
                .collect(Collectors.toList());

        return mappedParticipants;
    }

    void update(MeetingParticipation participant) {
        generalMeetingService.findById(participant.getGeneralMeeting().getId());
        repository.save(participant);
    }

    private Function<UserDto, MeetingParticipationDto> mapAllUserParticipations(List<Long> participantUserIds, Long meetingId) {
        return user -> {
            MeetingParticipationDto.MeetingParticipationDtoBuilder participationDtoBuilder = MeetingParticipationDto.builder()
                    .generalMeetingId(meetingId)
                    .user(user);
            if (participantUserIds.contains(user.getId())) {
                return participationDtoBuilder.participated(true).build();
            } else {
                return participationDtoBuilder.participated(false).build();
            }
        };
    }
}
