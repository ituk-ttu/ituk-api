package ee.ituk.api.join.domain;

import ee.ituk.api.user.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "application", schema = "public")
public class Application {

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
}
