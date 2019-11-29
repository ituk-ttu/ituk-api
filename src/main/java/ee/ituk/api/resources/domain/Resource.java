package ee.ituk.api.resources.domain;

import ee.ituk.api.user.domain.User;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "Resource")
@Table(schema = "public", name = "resource")
@SQLDelete(sql = "UPDATE resource SET deleted_at = now() WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE resource SET deleted_at = NOW() WHERE 1 = 1")
@Where(clause = "deleted_at IS null")
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
