package ee.ituk.api.meeting.participation;

import ee.ituk.api.meeting.general.GeneralMeeting;
import ee.ituk.api.user.domain.User;
import ee.ituk.api.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingParticipationDto {

    private Long id;
    private UserDto user;
    private Long generalMeetingId;
    private boolean participated;
    private boolean mandatory;

}
