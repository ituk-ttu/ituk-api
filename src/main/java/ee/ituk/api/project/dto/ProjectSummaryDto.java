package ee.ituk.api.project.dto;

import ee.ituk.api.user.dto.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectSummaryDto {
    private Long id;
    private LocalDateTime createdAt;
    private String positiveSummary;
    private String negativeSummary;
    private Long createdById;
    private Long confirmedById;
    private Long projectId;
}
