package ee.ituk.api.mentor;

import ee.ituk.api.mentor.domain.MentorProfile;
import ee.ituk.api.mentor.dto.MentorProfileDto;
import ee.ituk.api.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mentor")
@RequiredArgsConstructor
public class MentorProfileController {

    private final MentorProfileService mentorProfileService;
    private final MentorProfileMapper mentorProfileMapper = MentorProfileMapper.INSTANCE;

/*
  Should be unnecessary since we never really delete profiles
  @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteMentorProfile(@PathVariable long id) {
        mentorProfileService.deleteMentorProfile(id);
    }*/

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

/*
 Should be unnecessary since mentor profile is created automatically when user role is changed
 @PostMapping
    @ResponseBody
    public MentorProfileDto createMentorProfile(@RequestBody MentorProfileDto mentorProfileDto) {
        return mentorProfileMapper.mentorprofileToDto(
                mentorProfileService.createProfile(
                        mentorProfileMapper.mentorprofileDtoToEntity(mentorProfileDto)));
    }*/

    /*@ApiOperation(value = "Get all mentor profiles with users who are mentors based on their role (ADMIN, BOARD, MENTOR)")
    @GetMapping("")
    public List<MentorProfile> getAllActiveMentors() {
        return mentorProfileService.getAllActive();
    }*/

}
