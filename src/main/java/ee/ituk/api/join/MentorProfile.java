package ee.ituk.api.join;


import ee.ituk.api.user.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "mentor_profile", schema = "public")
public class MentorProfile {

  @Id
  @GeneratedValue
  private Long id;
  @OneToOne
  @JoinColumn(name = "userId")
  private User user;
  private String curriculum;
  private String text;
  private String gif;
  private String quote;
  private boolean enabled;
  private String pictureName;
  private LocalDate createdAt;
  private LocalDate updatedAt;
}
