package ee.ituk.api.mentor.domain;


import ee.ituk.api.user.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mentor_profile", schema = "public")
public class MentorProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;  //NB! THIS IS ONLY FOR JPA, FOR FUNCTIONALITY USE USER_ID
  @OneToOne
  @JoinColumn(name = "userId")
  private User user;
  private String text;
  private String gif;
  private String quote;
  private boolean enabled;
  private String pictureName;
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt = LocalDateTime.now();

  public MentorProfile(User user) {
      this.user = user;
  }
}
