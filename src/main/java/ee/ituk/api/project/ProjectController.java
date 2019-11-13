package ee.ituk.api.project;

import ee.ituk.api.project.dto.ProjectDto;
import ee.ituk.api.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);

    @GetMapping()
    public ResponseEntity findAll() {
        return ok(mapper.projectsToDto(projectService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity findProjectById(@PathVariable Long id) {
        return ok(mapper.projectToDto(projectService.findById(id)));
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody ProjectDto projectDto) {
        return ok(mapper.projectToDto(projectService.save(mapper.projectToEntity(projectDto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        // TODO: proper update
        return ok(mapper.projectToDto(projectService.save(mapper.projectToEntity(projectDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
