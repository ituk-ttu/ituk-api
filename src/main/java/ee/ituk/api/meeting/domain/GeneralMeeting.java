package ee.ituk.api.meeting.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class GeneralMeeting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private LocalDate date;
  private boolean election;
  private String protocolUrl;
  private LocalDate createdAt;
  private LocalDate updatedAt;
}
