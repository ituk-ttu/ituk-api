package ee.ituk.api.meeting;

import ee.ituk.api.meeting.domain.GeneralMeeting;
import ee.ituk.api.meeting.dto.GeneralMeetingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GeneralMeetingMapper {

    GeneralMeetingDto meetingToDto(GeneralMeeting meeting);

    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "updatedAt")
    GeneralMeeting dtoToMeeting(GeneralMeetingDto dto);
}
