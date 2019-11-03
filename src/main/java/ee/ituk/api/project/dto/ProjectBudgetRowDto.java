package ee.ituk.api.project.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProjectBudgetRowDto {
    private Long id;
    private String description;
    private BigDecimal itukCost;
    private BigDecimal facultyCost;
}
