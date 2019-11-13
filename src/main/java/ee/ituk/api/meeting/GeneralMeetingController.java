package ee.ituk.api.meeting;

import ee.ituk.api.meeting.domain.GeneralMeeting;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/meeting")
@RequiredArgsConstructor
public class GeneralMeetingController {

    private final GeneralMeetingService meetingService;

    @GetMapping("")
    public ResponseEntity getAllMeetings() {
        return ok(meetingService.getAll());
    }

    @PostMapping("")
    public ResponseEntity createMeeting(@RequestBody GeneralMeeting meeting) {
        return ok(meetingService.create(meeting));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMeeting(@PathVariable Long id, @RequestBody GeneralMeeting meeting) {
        return ok(meetingService.update(id, meeting));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMeeting(@PathVariable Long id) {
        meetingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
