package ee.ituk.api.application;

import ee.ituk.api.application.dto.ApplicationDto;
import ee.ituk.api.application.dto.ApplicationMentorRequest;
import ee.ituk.api.application.dto.ApplicationResponseDto;
import ee.ituk.api.application.dto.ChangeApplicationStatusRequest;
import ee.ituk.api.application.service.ApplicationService;
import ee.ituk.api.application.service.ApplicationsMentor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    private final ApplicationMapper applicationMapper = Mappers.getMapper(ApplicationMapper.class);

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteApplication(@PathVariable long id) {
        applicationService.deleteApplication(id);
        return noContent().build();
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<ApplicationDto> updateApplication(ApplicationDto applicationDto) {
        return ok(applicationMapper.applicationToDto(applicationService.updateApplication(applicationMapper.applicationToEntity(applicationDto))));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApplicationResponseDto> findApplicationById(@PathVariable long id) {
        return ok(applicationMapper.applicationToResponseDto(applicationService.findApplicationById(id)));
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ApplicationResponseDto>> findAllApplications() {
        return ok(applicationMapper.applicationsToResponseDto(applicationService.findAll()));
    }

    @PostMapping
    public ResponseEntity<ApplicationDto> createApplication(@RequestBody ApplicationDto applicationDto) {
        return ok(applicationMapper.applicationToDto(applicationService.createApplication(applicationMapper.applicationToEntity(applicationDto))));
    }

    @PutMapping("/mentor")
    public ResponseEntity<Void> setMentorToApplication(@RequestBody ApplicationMentorRequest request) {
        applicationService.setMentorToApplication(request.getApplicationId(), request.getMentorId());
        return noContent().build();
    }

    @GetMapping("{id}/minions")
    public ResponseEntity<List<ApplicationResponseDto>> findUsersMinions(@PathVariable Long id) {
        return ok(applicationMapper.applicationsToResponseDto(applicationService.findByMentor(id)));
    }

    @GetMapping("apply/{applicationId}/{selectionCode}")
    public ResponseEntity<ApplicationsMentor> applyApplication(@PathVariable Long applicationId, @PathVariable String selectionCode) {
        return ok(applicationService.apply(applicationId, selectionCode));
    }

    @PutMapping("{applicationId}/status")
    public ResponseEntity<Void> changeApplicationStatus(@PathVariable Long applicationId, @RequestBody ChangeApplicationStatusRequest request) {
        applicationService.changeApplicationStatus(applicationId, request);
        return noContent().build();
    }
}
