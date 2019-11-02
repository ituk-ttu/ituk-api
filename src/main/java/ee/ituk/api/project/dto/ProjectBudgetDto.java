package ee.ituk.api.project.dto;

import ee.ituk.api.project.domain.Project;
import ee.ituk.api.project.domain.ProjectBudgetRow;
import lombok.Data;

import java.util.List;

@Data
public class ProjectBudgetDto {
    private Long id;
    private Project project;
    private List<ProjectBudgetRow> projectBudgetRows;
}
