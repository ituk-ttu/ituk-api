package ee.ituk.api.door;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class DoorPermissionLogEntry {

  @Id
  @GeneratedValue
  private Long id;
  private String change;
  private LocalDateTime updatedAt;
  private Long modifiedBy;
}
