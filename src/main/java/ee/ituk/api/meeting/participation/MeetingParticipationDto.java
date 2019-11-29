package ee.ituk.api.meeting.participation;

import ee.ituk.api.meeting.general.GeneralMeeting;
import ee.ituk.api.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingParticipationDto {

    private Long id;
    private User user;
    private GeneralMeeting generalMeeting;
    private boolean participated;
    private boolean mandatory;
}
