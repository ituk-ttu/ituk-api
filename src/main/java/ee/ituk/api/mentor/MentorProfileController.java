package ee.ituk.api.mentor;

import ee.ituk.api.mentor.dto.MentorProfileDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mentor")
@RequiredArgsConstructor
public class MentorProfileController {

    private final MentorProfileService mentorProfileService;
    private final MentorProfileMapper mentorProfileMapper = MentorProfileMapper.INSTANCE;

    @PutMapping
    @ResponseBody
    public MentorProfileDto updateMentorProfile(@RequestBody MentorProfileDto mentorProfileDto) {
        return mentorProfileMapper.mentorprofileToDto(
                mentorProfileService.updateMentor(
                        mentorProfileMapper.mentorprofileDtoToEntity(mentorProfileDto)
                )
        );
    }

    @ApiOperation(value = "Find mentor by user id")
    @GetMapping("/{id}")
    @ResponseBody
    public MentorProfileDto getMentorProfileByUserId(@PathVariable long id) {
        return mentorProfileMapper.mentorprofileToDto(mentorProfileService.getByUserId(id));
    }

    @ApiOperation(value = "Get all mentor profiles for user with mentor role (MENTOR, BOARD, ADMIN)")
    @GetMapping
    @ResponseBody
    public List<MentorProfileDto> getAllActiveMentorProfiles() {
        return mentorProfileMapper.mentorprofilesToDto(mentorProfileService.getAllActive());
    }
}
