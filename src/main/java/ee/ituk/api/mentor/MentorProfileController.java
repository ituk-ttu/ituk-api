package ee.ituk.api.mentor;

import ee.ituk.api.mentor.domain.MentorProfile;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mentor")
@RequiredArgsConstructor
public class MentorProfileController {

    private final MentorProfileService mentorProfileService;

    @ApiOperation(value = "Get all mentor profiles with users who are mentors based on their role (ADMIN, BOARD, MENTOR)")
    @GetMapping("")
    public List<MentorProfile> getAllActiveMentors() {
        return mentorProfileService.getAllActive();
    }

}
