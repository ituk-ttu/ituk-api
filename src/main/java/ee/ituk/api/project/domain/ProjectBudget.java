package ee.ituk.api.project.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "project_budget", schema = "public")
public class ProjectBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "projectBudget", cascade = CascadeType.ALL)
    private List<ProjectBudgetRow> rows;
}
