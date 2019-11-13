package ee.ituk.api.meeting;

import ee.ituk.api.meeting.domain.GeneralMeeting;
import ee.ituk.api.meeting.dto.GeneralMeetingDto;
import org.mapstruct.Mapper;

@Mapper
public interface GeneralMeetingMapper {

    GeneralMeetingDto meetingToDto(GeneralMeeting meeting);

    GeneralMeeting dtoToMeeting(GeneralMeetingDto dto);
}
