package ee.ituk.api.project.dto;

import ee.ituk.api.user.dto.UserDto;
import lombok.Data;

@Data
public class ProjectMemberDto {
    private Long id;
    private Long projectId;
    private Long userId;
    private String name;
}
