package ee.ituk.api.meeting.participation;

import ee.ituk.api.meeting.general.GeneralMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MeetingParticipationRepository extends JpaRepository<MeetingParticipation, Long> {

    Optional<List<MeetingParticipation>> getAllByGeneralMeeting(GeneralMeeting meeting);

    Optional<List<MeetingParticipation>> getAllByGeneralMeetingAndParticipated(GeneralMeeting meeting, Boolean participated);

    @Query(value = "UPDATE meeting_participation SET expires_at = now() WHERE id = ?",
            nativeQuery = true)
    @Modifying
    void delete(Long participationId);
}
