package ee.ituk.api.project.dto;

import ee.ituk.api.project.domain.ProjectMember;
import ee.ituk.api.user.domain.User;
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
    private User projectLead;
    private List<ProjectMember> members;
}
