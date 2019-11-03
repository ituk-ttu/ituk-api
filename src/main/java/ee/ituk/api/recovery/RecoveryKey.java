package ee.ituk.api.recovery;


import ee.ituk.api.user.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "recovery_key", schema = "public")
public class RecoveryKey {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
  private Long id;
  private String key;
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt = LocalDateTime.now();
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
