package ee.ituk.api.meeting.agenda;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingAgendaRepository extends JpaRepository<MeetingAgenda, Long> {
}
