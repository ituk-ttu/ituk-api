package ee.ituk.api.user.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "user", schema = "public")
public class User {

  @Id
  @GeneratedValue
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String cardNumber;
  private String password;
  private String studentCode;
  @ManyToOne
  @JoinColumn( name = "status_id")
  private UserStatus status;
  private String curriculum;
  private String iban;
  @Enumerated(EnumType.STRING)
  private Role role;
  private boolean archived;
  private LocalDate createdAt;
  private LocalDate updatedAt;

}
