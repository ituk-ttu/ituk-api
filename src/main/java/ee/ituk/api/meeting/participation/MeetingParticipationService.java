package ee.ituk.api.meeting.participation;

import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.meeting.general.GeneralMeeting;
import ee.ituk.api.meeting.general.GeneralMeetingDto;
import ee.ituk.api.meeting.general.GeneralMeetingService;
import ee.ituk.api.user.UserMapper;
import ee.ituk.api.user.UserService;
import ee.ituk.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Transactional
    public List<MeetingParticipation> add(List<MeetingParticipationDto> participants) {
        Optional<Long> maybeGeneralMeeting = participants.stream().map(MeetingParticipationDto::getGeneralMeetingId).findFirst();
        Long generalMeetingId = maybeGeneralMeeting.orElseThrow(() -> new NotFoundException(singletonList(getNotFoundError(GeneralMeetingDto.class))));

        GeneralMeeting generalMeeting = generalMeetingService.findById(generalMeetingId);
        List<MeetingParticipation> existingParticipants = repository.getAllByGeneralMeeting(generalMeeting).orElse(emptyList());

        existingParticipants.forEach(part -> repository.delete(part.getId()));
        List<MeetingParticipation> entities = participantMapper.dtosToEntities(participants);
        return repository.saveAll(entities);
    }

    List<MeetingParticipationDto> getAllParticipantsByMeeting(Long id) {
        GeneralMeeting meeting = generalMeetingService.findById(id);
        List<MeetingParticipationDto> participants = participantMapper.entitiesToDtos(repository.getAllByGeneralMeetingAndParticipated(meeting, true).orElse(emptyList()));
        List<Long> participantUserIds = participants.stream()
                .map(p -> p.getUser().getId())
                .collect(Collectors.toList());
        List<UserDto> users = userMapper.usersToDto(userService.findAllByArchived(false));

        return users.stream()
                .map(mapAllUserParticipations(participantUserIds, id))
                .sorted(Comparator.comparing(MeetingParticipationDto::isParticipated).reversed()) // first true then false
                .collect(Collectors.toList());
    }

    private Function<UserDto, MeetingParticipationDto> mapAllUserParticipations(List<Long> participantUserIds, Long meetingId) {
        return user -> {
            MeetingParticipationDto.MeetingParticipationDtoBuilder participationDtoBuilder = MeetingParticipationDto.builder()
                    .generalMeetingId(meetingId)
                    .user(user);
            return participantUserIds.contains(user.getId())
                    ? participationDtoBuilder.participated(true).build()
                    : participationDtoBuilder.participated(false).build();
        };
    }
}
