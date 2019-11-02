package ee.ituk.api.project;


import ee.ituk.api.project.domain.Project;
import ee.ituk.api.project.dto.ProjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);
    ProjectDto projectToDto(Project project);
    Project projectToEntity(ProjectDto projectDto);
    List<ProjectDto> projectsToDto(List<Project> projects);
}
