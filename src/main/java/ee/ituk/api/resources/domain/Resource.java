package ee.ituk.api.resources.domain;

import ee.ituk.api.user.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(schema = "public", name = "resource")
public class Resource {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String comment;
  private String url;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;
}
