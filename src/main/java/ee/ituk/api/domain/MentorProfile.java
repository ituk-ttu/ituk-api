package ee.ituk.api.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class MentorProfile {

  @Id
  @GeneratedValue
  private Long id;
  private String curriculum;
  private String text;
  private String gif;
  private String quote;
  private boolean enabled;
  private String pictureName;
  private LocalDate createdAt;
  private LocalDate updatedAt;
}
