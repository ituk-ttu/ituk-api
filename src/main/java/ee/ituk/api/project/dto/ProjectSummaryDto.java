package ee.ituk.api.project.dto;

import ee.ituk.api.project.domain.Project;
import ee.ituk.api.user.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectSummaryDto {
    private Long id;
    private LocalDateTime createdAt;
    private String positiveSummary;
    private String negativeSummary;
    private Project project;
    private User createdBy;
    private User confirmedBy;
}
