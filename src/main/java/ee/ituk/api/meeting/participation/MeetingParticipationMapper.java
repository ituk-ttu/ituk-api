package ee.ituk.api.meeting.participation;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MeetingParticipationMapper {

    MeetingParticipationDto entityToDto(MeetingParticipation meeting);

    MeetingParticipation dtoToEntity(MeetingParticipationDto dto);

    List<MeetingParticipation> dtosToEntities(List<MeetingParticipation> dto);
}
