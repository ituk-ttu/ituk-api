package ee.ituk.api.application;

import ee.ituk.api.application.dto.ApplicationDto;
import ee.ituk.api.application.dto.ApplicationMentorRequest;
import ee.ituk.api.application.dto.ChangeApplicationStatusRequest;
import ee.ituk.api.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity deleteApplication(@PathVariable long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity updateApplication(ApplicationDto applicationDto) {
        return ok(applicationMapper.applicationToDto(applicationService.updateProfile(applicationMapper.applicationToEntity(applicationDto))));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity findApplicationById(@PathVariable long id) {
        return ok(applicationMapper.applicationToResponseDto(applicationService.findApplicationById(id)));
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity findAllApplications() {
        return ok(applicationMapper.applicationsToResponseDto(applicationService.findAll()));
    }

    @PostMapping
    public ResponseEntity createApplication(@RequestBody ApplicationDto applicationDto) {
        return ok(applicationMapper.applicationToDto(applicationService.createApplication(applicationMapper.applicationToEntity(applicationDto))));
    }

    @PutMapping("/mentor")
    public ResponseEntity setMentorToApplication(@RequestBody ApplicationMentorRequest request) {
        applicationService.setMentorToApplication(request.getApplicationId(), request.getMentorId());
        return ok().build();
    }

    @GetMapping("{id}/minions")
    public ResponseEntity findUsersMinions(@PathVariable Long id) {
        return ok(applicationMapper.applicationsToResponseDto(applicationService.findByMentor(id)));
    }

    @GetMapping("apply/{applicationId}/{selectionCode}")
    public ResponseEntity applyApplication(@PathVariable Long applicationId, @PathVariable String selectionCode) {
        return ok(applicationService.apply(applicationId, selectionCode));
    }

    @PutMapping("{applicationId}/status")
    public ResponseEntity changeApplicationStatus(@PathVariable Long applicationId, @RequestBody ChangeApplicationStatusRequest request) {
        applicationService.changeApplicationStatus(applicationId, request);
        return noContent().build();
    }
}
