package ee.ituk.api.project;


import ee.ituk.api.project.domain.Project;
import ee.ituk.api.project.domain.ProjectBudgetRow;
import ee.ituk.api.project.domain.ProjectMember;
import ee.ituk.api.project.dto.ProjectBudgetRowDto;
import ee.ituk.api.project.dto.ProjectDto;
import ee.ituk.api.project.dto.ProjectMemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(target = "projectLeadId", source = "projectLead.id")
    @Mapping(target = "summary.createdById", source = "summary.createdBy.id")
    @Mapping(target = "summary.confirmedById", source = "summary.confirmedBy.id")
    @Mapping(target = "budget.projectId", source = "budget.project.id")
    @Mapping(target = "summary.projectId", source = "summary.project.id")
    ProjectDto projectToDto(Project project);

    @Mapping(target = "projectLead.password", ignore = true)
    @Mapping(target = "projectLead.authorities", ignore = true)
    @Mapping(target = "projectLead.id", source = "projectLeadId")
    @Mapping(target = "summary.createdBy.id", source = "summary.createdById")
    @Mapping(target = "summary.project.id", source = "summary.projectId")
    @Mapping(target = "summary.confirmedBy.id", source = "summary.confirmedById")
    @Mapping(target = "budget.project.id", source = "budget.projectId")
    Project projectToEntity(ProjectDto projectDto);

    List<ProjectDto> projectsToDto(List<Project> projects);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "projectId", source = "project.id")
    ProjectMemberDto projectMemberToDto(ProjectMember projectMember);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "project.id", source = "projectId")
    ProjectMember projectMemberToEntity(ProjectMemberDto projectMemberDto);

    @Mapping(target = "projectBudgetId", source = "projectBudget.id")
    ProjectBudgetRowDto projectBudgetRowToDto(ProjectBudgetRow projectBudgetRow);

    @Mapping(target = "projectBudget.id", source = "projectBudgetId")
    ProjectBudgetRow projectBudgetRowDtoToEntity(ProjectBudgetRowDto projectBudgetRowDto);
}
