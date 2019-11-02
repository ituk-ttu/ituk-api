package ee.ituk.api.project.dto;

import ee.ituk.api.project.domain.ProjectBudget;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProjectBudgetRowDto {
    private Long id;
    private ProjectBudget projectBudget;
    private String description;
    private BigDecimal itukCost;
    private BigDecimal facultyCost;
}
