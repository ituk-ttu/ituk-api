package ee.ituk.api.project;

import ee.ituk.api.project.dto.ProjectCreationSpec;
import ee.ituk.api.project.dto.ProjectSummaryDto;
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

    @GetMapping("/{id}/summary")
    public ResponseEntity getProjectSummaryByProjectId(@PathVariable Long id) {
        return ok(mapper.projectSummaryToDto(projectService.findSummaryById(id)));
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody ProjectCreationSpec projectDto) {
        return ok(mapper.projectToDto(projectService.create(mapper.projectToEntity(projectDto))));
    }

    @PostMapping("/{id}/summary")
    public ResponseEntity addSummaryToProject(@RequestBody ProjectSummaryDto projectSummaryDto, @PathVariable Long id) {
        return ok(mapper.projectSummaryToDto(projectService.addSummary(id, mapper.projectSummaryToEntity(projectSummaryDto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody ProjectCreationSpec projectDto) {
        return ok(mapper.projectToDto(projectService.update(mapper.projectToEntity(projectDto), id)));
    }

    @PutMapping("/{id}/summary")
    public ResponseEntity updateProjectSummary(@PathVariable Long id, @RequestBody ProjectSummaryDto projectSummaryDto) {
        return ok(mapper.projectSummaryToDto(projectService.updateSummary(id, mapper.projectSummaryToEntity(projectSummaryDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/summary")
    public ResponseEntity deleteProjectSummary(@PathVariable Long id) {
        projectService.deleteSummary(id);
        return ResponseEntity.noContent().build();
    }
}
