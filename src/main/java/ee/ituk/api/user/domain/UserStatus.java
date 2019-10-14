package ee.ituk.api.user.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class UserStatus {

  @Id
  @GeneratedValue
  private Long id;
  private String statusName;
  private String description;
}
