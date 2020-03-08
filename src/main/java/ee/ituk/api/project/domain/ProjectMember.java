package ee.ituk.api.project.domain;

import ee.ituk.api.user.domain.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "project_member", schema = "public")
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_summary_id", nullable = false)
    private ProjectSummary projectSummary;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
}
