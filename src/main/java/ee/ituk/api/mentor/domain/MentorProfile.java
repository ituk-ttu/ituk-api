package ee.ituk.api.mentor.domain;


import ee.ituk.api.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "MentorProfile")
@Table(name = "mentor_profile", schema = "public")
@SQLDelete(sql = "UPDATE mentor_profile SET deleted_at = now() WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE mentor_profile SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS null")
@NoArgsConstructor
public class MentorProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;  //NB! THIS IS ONLY FOR JPA, FOR FUNCTIONALITY USE USER_ID
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
  private String text;
  private String gif;
  private String quote;
  private boolean enabled;
  private String picture;
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt = LocalDateTime.now();

  public MentorProfile(User user) {
      this.user = user;
  }
}
