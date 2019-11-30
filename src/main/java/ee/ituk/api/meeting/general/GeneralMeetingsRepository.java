package ee.ituk.api.meeting.general;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralMeetingsRepository extends JpaRepository<GeneralMeeting, Long> {
}
