package ee.ituk.api.door.domain;

import ee.ituk.api.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoorPermissionLogEntry {

  @Id
  @GeneratedValue
  private Long id;
  private String change;
  private LocalDateTime updatedAt;
  @ManyToOne
  @JoinColumn(name = "modified_by")
  private User userModified;
}
