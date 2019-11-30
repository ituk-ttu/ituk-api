package ee.ituk.api.door.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "door", schema = "public")
public class Door {

  @Id
  private String code;

}
