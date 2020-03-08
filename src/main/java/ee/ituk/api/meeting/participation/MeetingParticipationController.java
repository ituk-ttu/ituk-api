package ee.ituk.api.meeting.participation;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("meeting/participation")
@RequiredArgsConstructor
public class MeetingParticipationController {

    private final MeetingParticipationService service;

    private final MeetingParticipationMapper mapper = Mappers.getMapper(MeetingParticipationMapper.class);

    @PutMapping
    public ResponseEntity<List<MeetingParticipationDto>> updateParticipations(@RequestBody List<MeetingParticipationDto> dtos) {
        return ok(mapper.entitiesToDtos(service.add(mapper.dtosToEntities(dtos))));
    }

    @GetMapping("/{meetingId}/all")
    public ResponseEntity<List<MeetingParticipationDto>> getAllParticipantsInAMeeting(@PathVariable Long meetingId) {
        return ok(mapper.entitiesToDtos(service.getAllParticipantsByMeeting(meetingId)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteParticipant(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
