package ee.ituk.api.door;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class DoorPermissionLogEntry {

  @Id
  @GeneratedValue
  private Long id;
  private String change;
  private LocalDate updatedAt;
  private Long modifiedBy;
}
