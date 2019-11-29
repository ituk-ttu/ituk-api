package ee.ituk.api.door.domain;


import ee.ituk.api.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
