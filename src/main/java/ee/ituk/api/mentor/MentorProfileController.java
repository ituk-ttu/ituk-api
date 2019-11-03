package ee.ituk.api.mentor;

import ee.ituk.api.mentor.dto.MentorProfileDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mentor")
@RequiredArgsConstructor
public class MentorProfileController {

    private final MentorProfileService mentorProfileService;
    private final MentorProfileMapper mentorProfileMapper = Mappers.getMapper(MentorProfileMapper.class);

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteMentorProfile(@PathVariable long id) {
        mentorProfileService.deleteMentorProfile(id);
    }

    @PutMapping
    @ResponseBody
    public MentorProfileDto updateMentorProfile(MentorProfileDto mentorProfileDto) {
        return mentorProfileMapper.mentorprofileToDto(
                mentorProfileService.updateMentor(
                        mentorProfileMapper.mentorprofileDtoToEntity(mentorProfileDto)
                )
        );
    }

    @ApiOperation(value="Find mentor by user id")
    @GetMapping("/{id}")
    @ResponseBody
    public MentorProfileDto getMentorProfileByUserId(@PathVariable long id) {
        return mentorProfileMapper.mentorprofileToDto(mentorProfileService.getByUserId(id));
    }

    @GetMapping
    @ResponseBody
    public List<MentorProfileDto> getAllMentorProfiles() {
        return mentorProfileMapper.mentorprofilesToDto(mentorProfileService.getAll());
    }

    @PostMapping
    @ResponseBody
    public MentorProfileDto createMentorProfile(@RequestBody MentorProfileDto mentorProfileDto) {
        return mentorProfileMapper.mentorprofileToDto(
                mentorProfileService.createProfile(
                        mentorProfileMapper.mentorprofileDtoToEntity(mentorProfileDto)));
    }

    /*@ApiOperation(value = "Get all mentor profiles with users who are mentors based on their role (ADMIN, BOARD, MENTOR)")
    @GetMapping("")
    public List<MentorProfile> getAllActiveMentors() {
        return mentorProfileService.getAllActive();
    }*/

}
