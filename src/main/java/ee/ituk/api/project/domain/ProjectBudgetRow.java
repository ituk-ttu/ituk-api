package ee.ituk.api.project.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "project_budget_row", schema = "public")
public class ProjectBudgetRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_budget_id")
    private ProjectBudget projectBudget;

    @NotBlank
    private String description;

    private BigDecimal itukCost;

    private BigDecimal facultyCost;
}
