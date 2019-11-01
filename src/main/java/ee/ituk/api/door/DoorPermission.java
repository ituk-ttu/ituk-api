package ee.ituk.api.door;


import ee.ituk.api.user.domain.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class DoorPermission {

  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  @JoinColumn(name = "door_code")
  private Door door;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
