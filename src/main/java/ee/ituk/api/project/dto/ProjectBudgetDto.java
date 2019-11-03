package ee.ituk.api.project.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectBudgetDto {
    private Long id;
    private Long projectId;
    private List<ProjectBudgetRowDto> rows;
}
