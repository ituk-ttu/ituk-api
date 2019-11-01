package ee.ituk.api.project.domain;

import ee.ituk.api.user.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="project_summary", schema = "public")
public class ProjectSummary {

    @Id
    @GeneratedValue
    private Long id;

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
