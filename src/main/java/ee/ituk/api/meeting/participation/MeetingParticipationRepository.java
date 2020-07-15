package ee.ituk.api.meeting.participation;

import ee.ituk.api.meeting.general.GeneralMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeetingParticipationRepository extends JpaRepository<MeetingParticipation, Long> {

    Optional<List<MeetingParticipation>> getAllByGeneralMeetingAndParticipatedAndExpiresAtIsNull(GeneralMeeting meeting, Boolean participated);
}
