package ee.ituk.api.meeting.agenda;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "meeting_agenda_item", schema = "public")
public class MeetingAgendaItem {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meeting_agenda_id")
    private MeetingAgenda meetingAgenda;
    private String item;
    private String order;
}
