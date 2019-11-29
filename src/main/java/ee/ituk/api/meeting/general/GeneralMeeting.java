package ee.ituk.api.meeting.general;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "general_meeting", schema = "public")
@SQLDelete(sql = "UPDATE general_meeting SET deleted_at = now() WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE general_meeting SET deleted_at = NOW() WHERE 1 = 1")
@Where(clause = "deleted_at IS null")
public class GeneralMeeting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private LocalDate date;
  private boolean election;
  private String protocolUrl;
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt = LocalDateTime.now();
}
