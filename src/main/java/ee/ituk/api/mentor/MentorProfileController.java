package ee.ituk.api.mentor;

import ee.ituk.api.mentor.dto.MentorProfileDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/mentor")
@RequiredArgsConstructor
public class MentorProfileController {

    public static final String BASE64_SEPARATOR = ",";
    public static final int BASE64_ENCODED_DATA = 1;
    private final MentorProfileService mentorProfileService;
    private final MentorProfileMapper mapper = Mappers.getMapper(MentorProfileMapper.class);

    @PutMapping
    @ResponseBody
    public MentorProfileDto updateMentorProfile(@RequestBody MentorProfileDto mentorProfileDto) {
        return mapper.mentorProfileToDto(
                mentorProfileService.updateMentor(mapper.mentorProfileDtoToEntity(mentorProfileDto))
        );
    }

    /**
     * @param id of mentor
     * @return base64 string(mentors pic) that will be fed to ng-hub's ng-img-cropper
     */
    @GetMapping(value = "{id}/base64")
    public String getMentorImageBase64(@PathVariable("id") Long id) {
        return this.mentorProfileService.loadPicture(id);
    }

    @GetMapping(value = "{id}/picture")
    public void getMentorImage(@PathVariable("id") Long id,
                               HttpServletResponse response) throws IOException {
        final String pic = this.mentorProfileService.loadPicture(id);
        String[] parts = pic.split(BASE64_SEPARATOR);
        String imageString = parts[BASE64_ENCODED_DATA];
        byte[] imageByte = Base64.getMimeDecoder().decode(imageString);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + "mentor.png");
        /* Convert bytes to stream of objects*/
        InputStream is = new ByteArrayInputStream(imageByte);
        /* Download copying the content to destination file*/
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
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

    @ApiOperation(value = "Find mentor by id")
    @GetMapping("{id}")
    @ResponseBody
    public MentorProfileDto getMentorProfileById(@PathVariable long id) {
        return mapper.mentorProfileToDto(mentorProfileService.getByMentorId(id));
    }

    @ApiOperation(value = "Find mentor by user id")
    @GetMapping("user/{id}")
    @ResponseBody
    public MentorProfileDto getMentorProfileByUserId(@PathVariable long id) {
        return mapper.mentorProfileToDto(mentorProfileService.getByUserId(id));
    }

    @ApiOperation(value = "Get all mentor profiles for user with isMentor true")
    @GetMapping("/active")
    @ResponseBody
    public List<MentorProfileDto> getAllActiveMentorProfiles() {
        return mapper.mentorProfilesToDto(mentorProfileService.getAllActive());
    }

    @ApiOperation(value = "Get all mentor profiles")
    @GetMapping
    @ResponseBody
    public List<MentorProfileDto> getAll() {
        return mapper.mentorProfilesToDto(mentorProfileService.getAll());
    }
}
