package ee.ituk.api.meeting.participation;

import ee.ituk.api.meeting.general.GeneralMeeting;
import ee.ituk.api.meeting.general.GeneralMeetingService;
import ee.ituk.api.user.UserMapper;
import ee.ituk.api.user.UserService;
import ee.ituk.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class MeetingParticipationService {

    private final GeneralMeetingService generalMeetingService;
    private final UserService userService;
    private final MeetingParticipationRepository repository;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final MeetingParticipationMapper participantMapper = Mappers.getMapper(MeetingParticipationMapper.class);

    public MeetingParticipation add(MeetingParticipationDto dto) {
        return repository.save(participantMapper.dtoToEntity(dto));
    }

    List<MeetingParticipationDto> getAllParticipantsByMeeting(Long id) {
        GeneralMeeting meeting = generalMeetingService.findById(id);
        List<MeetingParticipationDto> participants = participantMapper.entitiesToDtos(repository.getAllByGeneralMeetingAndParticipatedAndExpiresAtIsNull(meeting, true).orElse(emptyList()));
        List<UserDto> users = userMapper.usersToDto(userService.findAllByArchived(false));

        return users.stream()
                .map(mapParticipationToUser(participants, id))
                .sorted(Comparator.comparing(MeetingParticipationDto::isParticipated).reversed()) // first true then false
                .collect(Collectors.toList());
    }

    private Function<UserDto, MeetingParticipationDto> mapParticipationToUser(List<MeetingParticipationDto> participants, Long meetingId) {
        return user -> {
            MeetingParticipationDto.MeetingParticipationDtoBuilder participationDtoBuilder = MeetingParticipationDto.builder()
                    .generalMeetingId(meetingId)
                    .participated(false)
                    .user(user);

            if (participants.isEmpty()) return participationDtoBuilder.build();

            Optional<MeetingParticipationDto> maybeParticipant = participants.stream().filter(participant -> participant.getUser().equals(user))
                    .findFirst();
            if (maybeParticipant.isEmpty()) return participationDtoBuilder.build();
            MeetingParticipationDto participant = maybeParticipant.get();

            return participationDtoBuilder
                    .id(participant.getId())
                    .participated(participant.isParticipated())
                    .build();
        };
    }
}
