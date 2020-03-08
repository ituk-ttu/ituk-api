package ee.ituk.api.project.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectSummaryDto {
    private Long id;
    private Long projectId;
    private LocalDateTime createdAt;
    private String positiveSummary;
    private String negativeSummary;
    private Long createdById;
    private Long confirmedById;
    private List<ProjectMemberDto> members;
    private List<ProjectBudgetRowDto> budgetRows;
}
