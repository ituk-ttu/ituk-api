package ee.ituk.api.project;

import ee.ituk.api.project.dto.ProjectDto;
import ee.ituk.api.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper Mapper = Mappers.getMapper(ProjectMapper.class);

    @GetMapping()
    public List<ProjectDto> findAll() {
        return Mapper.projectsToDto(projectService.findAll());
    }

    @GetMapping("/{id}")
    public ProjectDto findProjectById(@PathVariable Long id) {
        return Mapper.projectToDto(projectService.findById(id));
    }

    @PostMapping()
    public ProjectDto create(@RequestBody ProjectDto projectDto) {
        return Mapper.projectToDto(
                projectService.save(Mapper.projectToEntity(projectDto))
        );
    }

    @PutMapping("/{id}")
    public ProjectDto update(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        return Mapper.projectToDto(
                projectService.save(Mapper.projectToEntity(projectDto))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        projectService.delete(id);
    }
}
