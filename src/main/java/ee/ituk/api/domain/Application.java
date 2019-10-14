package ee.ituk.api.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
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

  private Long processedById;
  private Long mentorId;
}
