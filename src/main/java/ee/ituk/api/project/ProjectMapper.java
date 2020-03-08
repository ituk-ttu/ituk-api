package ee.ituk.api.project;


import ee.ituk.api.project.domain.Project;
import ee.ituk.api.project.domain.ProjectBudgetRow;
import ee.ituk.api.project.domain.ProjectMember;
import ee.ituk.api.project.domain.ProjectSummary;
import ee.ituk.api.project.dto.*;

import org.mapstruct.*;

import java.util.List;

@Mapper
public interface ProjectMapper {

    @Mapping(target = "summary.createdById", source = "summary.createdBy.id")
    @Mapping(target = "summary.confirmedById", source = "summary.confirmedBy.id")
    @Mapping(target = "summary.projectId", source = "summary.project.id")
    ProjectDto projectToDto(Project project);

    @Mapping(target = "projectLead.password", ignore = true)
    @Mapping(target = "projectLead.authorities", ignore = true)
    @Mapping(target = "projectLead.id", source = "projectLeadId")
    Project projectToEntity(ProjectCreationSpec projectDto);

    @Mapping(target = "project.id", source = "projectId")
    @Mapping(target = "createdBy.id", source = "createdById")
    @Mapping(target = "confirmedBy.id", source = "confirmedById")
    ProjectSummary projectSummaryToEntity(ProjectSummaryDto projectSummaryDto);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "createdById", source = "createdBy.id")
    @Mapping(target = "confirmedById", source = "confirmedBy.id")
    ProjectSummaryDto projectSummaryToDto(ProjectSummary projectSummary);

    List<ProjectDto> projectsToDto(List<Project> projects);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "projectSummaryId", source = "projectSummary.id")
    ProjectMemberDto projectMemberToDto(ProjectMember projectMember);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "projectSummary.id", source = "projectSummaryId")
    ProjectMember projectMemberToEntity(ProjectMemberDto projectMemberDto);

    @Mapping(target = "projectSummaryId", source = "projectSummary.id")
    ProjectBudgetRowDto projectBudgetRowToDto(ProjectBudgetRow projectBudgetRow);

    @Mapping(target = "projectSummary.id", source = "projectSummaryId")
    ProjectBudgetRow projectBudgetRowDtoToEntity(ProjectBudgetRowDto projectBudgetRowDto);
}
