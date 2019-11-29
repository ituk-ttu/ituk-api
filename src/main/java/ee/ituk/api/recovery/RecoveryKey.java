package ee.ituk.api.recovery;


import ee.ituk.api.user.domain.User;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "RecoveryKey")
@Table(name = "recovery_key", schema = "public")
@SQLDelete(sql = "UPDATE recovery_key SET deleted_at = now() WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE recovery_key SET deleted_at = NOW() WHERE 1 = 1")
@Where(clause = "deleted_at IS null")
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
