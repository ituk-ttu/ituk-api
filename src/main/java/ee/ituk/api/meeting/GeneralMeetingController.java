package ee.ituk.api.meeting;

import ee.ituk.api.meeting.domain.GeneralMeeting;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meeting")
@RequiredArgsConstructor
public class GeneralMeetingController {

    private final GeneralMeetingService meetingService;

    @GetMapping("")
    public List<GeneralMeeting> getAllMeetings() {
        return meetingService.getAll();
    }

    @PostMapping("")
    public GeneralMeeting createMeeting(@RequestBody GeneralMeeting meeting) {
        return meetingService.create(meeting);
    }

}
