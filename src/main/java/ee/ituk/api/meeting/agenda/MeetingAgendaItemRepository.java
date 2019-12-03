package ee.ituk.api.meeting.agenda;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingAgendaItemRepository extends JpaRepository<MeetingAgendaItem, Long> {
}
