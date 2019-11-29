package ee.ituk.api.project.domain;

import ee.ituk.api.user.domain.User;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity(name = "ProjectSummary")
@Table(name = "project_summary", schema = "public")
@SQLDelete(sql = "UPDATE project_summary SET deleted_at = now() WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE project_summary SET deleted_at = NOW() WHERE 1 = 1")
@Where(clause = "deleted_at IS null")
public class ProjectSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime createdAt;

    private String positiveSummary;

    private String negativeSummary;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "confirmed_by")
    private User confirmedBy;

}
