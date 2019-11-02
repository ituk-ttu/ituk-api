package ee.ituk.api.project.domain;

import ee.ituk.api.user.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "project", schema = "public")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate dateStart;

    private LocalDate dateEnd;

    private String description;

    @ManyToOne
    @JoinColumn(name = "project_lead_id")
    private User projectLead;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectMember> members;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private ProjectBudget budget;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private ProjectSummary summary;
}
