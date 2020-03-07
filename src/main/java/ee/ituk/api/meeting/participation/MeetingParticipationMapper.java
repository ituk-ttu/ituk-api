package ee.ituk.api.meeting.participation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MeetingParticipationMapper {

    @Mapping(target = "generalMeetingId", source = "generalMeeting.id")
    MeetingParticipationDto entityToDto(MeetingParticipation meeting);

    @Mapping(target = "generalMeeting.id", source = "generalMeetingId")
    MeetingParticipation dtoToEntity(MeetingParticipationDto dto);

    List<MeetingParticipation> dtosToEntities(List<MeetingParticipationDto> dto);

    List<MeetingParticipationDto> entitiesToDtos(List<MeetingParticipation> meetings);
}
