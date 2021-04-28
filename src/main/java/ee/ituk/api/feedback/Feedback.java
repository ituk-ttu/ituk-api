package ee.ituk.api.feedback;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Table(name = "feedback", schema = "public")
@SQLDelete(sql = "UPDATE feedback SET deleted_at = NOW()")
@SQLDeleteAll(sql = "UPDATE feedback SET deleted_at = NOW()")
@Where(clause = "deleted_at IS null")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;
    private Instant createdAt;

}
