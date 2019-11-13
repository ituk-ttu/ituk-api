package ee.ituk.api.door;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
class Door {

  @Id
  private String code;

}
