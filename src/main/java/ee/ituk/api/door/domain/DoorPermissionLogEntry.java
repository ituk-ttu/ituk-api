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
@Table(name = "door_permission_log_entry", schema = "public")
public class DoorPermissionLogEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String change;
  private LocalDateTime updatedAt;
  @ManyToOne
  @JoinColumn(name = "modified_by")
  private User userModified;
}
