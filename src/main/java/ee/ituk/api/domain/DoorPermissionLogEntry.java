package ee.ituk.api.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DoorPermissionLogEntry {

  private Long id;
  private String change;
  private LocalDate updatedAt;
  private Long modifiedBy;
}
