package ee.ituk.api.meeting.agenda;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "meeting_agenda", schema = "public")
@SQLDelete(sql = "UPDATE meeting_agenda SET deleted_at = now() WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE meeting_agenda SET deleted_at = NOW() WHERE 1 = 1")
@Where(clause = "deleted_at IS null")
public class MeetingAgenda {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "meetingAgenda", fetch = FetchType.LAZY)
    private List<MeetingAgendaItem> items = new ArrayList<>();
}
