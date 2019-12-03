package ee.ituk.api.mentor;

import ee.ituk.api.mentor.dto.MentorProfileDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/mentor")
@RequiredArgsConstructor
public class MentorProfileController {

    private final MentorProfileService mentorProfileService;
    private final MentorProfileMapper mapper = Mappers.getMapper(MentorProfileMapper.class);

    @PutMapping
    @ResponseBody
    public MentorProfileDto updateMentorProfile(@RequestBody MentorProfileDto mentorProfileDto) {
        return mapper.mentorProfileToDto(
                mentorProfileService.updateMentor(mapper.mentorProfileDtoToEntity(mentorProfileDto))
        );
    }

    @GetMapping("{id}/picture")
    public ResponseEntity<Resource> downloadPicture(@PathVariable("id") String id) {
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("application/png"))
                .body(this.mentorProfileService.loadPicture(id));
    }

    @PutMapping("{id}/picture")
    public ResponseEntity uploadPicture(@PathVariable("id") String id, @RequestPart(required = false) MultipartFile file) {
        this.mentorProfileService.uploadPicture(id, file);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/picture/base64")
    public ResponseEntity uploadPicture(@PathVariable("id") Long id, @RequestBody(required = false) String file) {
        this.mentorProfileService.setNewBase64ProfileImage(id, file);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Find mentor by user id")
    @GetMapping("/{id}")
    @ResponseBody
    public MentorProfileDto getMentorProfileByUserId(@PathVariable long id) {
        return mapper.mentorProfileToDto(mentorProfileService.getByUserId(id));
    }

    @ApiOperation(value = "Get all mentor profiles for user with mentor role (MENTOR, BOARD, ADMIN)")
    @GetMapping
    @ResponseBody
    public List<MentorProfileDto> getAllActiveMentorProfiles() {
        return mapper.mentorProfilesToDto(mentorProfileService.getAllActive());
    }
}
