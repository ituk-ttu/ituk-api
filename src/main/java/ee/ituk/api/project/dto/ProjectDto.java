package ee.ituk.api.project.dto;

import ee.ituk.api.user.dto.UserDto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectDto {
    private Long id;
    private String title;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String description;
    private Long projectLeadId;
    private List<ProjectMemberDto> members;
    private ProjectBudgetDto budget;
    private ProjectSummaryDto summary;
}
