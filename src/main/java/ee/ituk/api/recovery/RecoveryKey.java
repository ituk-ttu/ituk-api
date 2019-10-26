package ee.ituk.api.recovery;


import ee.ituk.api.user.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "recovery_key", schema = "public")
public class RecoveryKey {

  @Id
  @GeneratedValue
  private Long id;
  private String key;
  private LocalDate createdAt;
  private LocalDate updatedAt;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
