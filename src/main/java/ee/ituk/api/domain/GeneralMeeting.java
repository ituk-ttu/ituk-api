package ee.ituk.api.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class GeneralMeeting {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private LocalDate date;
  private LocalDate createdAt;
  private LocalDate updatedAt;
}
