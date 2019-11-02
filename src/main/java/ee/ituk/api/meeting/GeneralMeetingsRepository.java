package ee.ituk.api.meeting;

import ee.ituk.api.meeting.domain.GeneralMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralMeetingsRepository extends JpaRepository<GeneralMeeting, Long> {
}
