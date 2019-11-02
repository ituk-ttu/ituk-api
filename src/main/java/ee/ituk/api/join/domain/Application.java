package ee.ituk.api.join.domain;

import ee.ituk.api.user.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "application", schema = "public")
public class Application {

  @Id
  @GeneratedValue
  private Long id;
  private String firstName;
  private String lastName;
  private String personalCode;
  private String email;
  private String studentCode;
  private String curriculum;
  private String mentorSelectionCode;
  private LocalDate createdAt;
  private LocalDate updatedAt;
  @ManyToOne
  @JoinColumn(name = "processedById")
  private User processedBy;
  @ManyToOne
  @JoinColumn(name = "mentorId")
  private MentorProfile mentor;
}
