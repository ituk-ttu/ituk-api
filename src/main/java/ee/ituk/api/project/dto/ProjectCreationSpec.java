package ee.ituk.api.project.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectCreationSpec {
    private String title;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String description;
    private Long projectLeadId;
}
