package ee.ituk.api.project.dto;

import ee.ituk.api.project.domain.Project;
import ee.ituk.api.user.domain.User;
import lombok.Data;

@Data
public class ProjectMemberDto {
    private Long id;
    private Project project;
    private User user;
    private String name;
}
