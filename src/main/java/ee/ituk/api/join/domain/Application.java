package ee.ituk.api.join.domain;

import ee.ituk.api.common.domain.PersonalData;
import ee.ituk.api.user.domain.User;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "Application")
@Table(name = "application", schema = "public")
@SQLDelete(sql = "UPDATE application SET deleted_at = now() WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE application SET deleted_at = NOW() WHERE 1 = 1")
@Where(clause = "deleted_at IS null")

public class Application implements PersonalData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String firstName;
  private String lastName;
  private String personalCode;
  private String email;
  private String studentCode;
  private String curriculum;
  private String mentorSelectionCode;
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt = LocalDateTime.now();
  @ManyToOne
  @JoinColumn(name = "processedById")
  private User processedBy;
  @ManyToOne
  @JoinColumn(name = "mentor_id", referencedColumnName = "id")
  private User mentor;
  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
