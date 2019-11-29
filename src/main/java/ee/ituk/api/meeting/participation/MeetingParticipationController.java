package ee.ituk.api.meeting.participation;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("meeting/participation")
@RequiredArgsConstructor
public class MeetingParticipationController {

    private final MeetingParticipationService service;

    private final MeetingParticipationMapper mapper = Mappers.getMapper(MeetingParticipationMapper.class);

    @PostMapping
    public ResponseEntity addParticipant(@RequestBody MeetingParticipationDto dto) {
        return ok(mapper.entityToDto(service.add(mapper.dtoToEntity(dto))));
    }

    @GetMapping("{meetingId}/all")
    public ResponseEntity getAllParticipantsInAMeeting(@PathVariable Long meetingId) {
        return ok(mapper.dtosToEntities(service.getAllParticipantsByMeeting(meetingId)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteParticipant(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity updateParticipant(@RequestBody MeetingParticipation participant) {
        service.update(participant);
        return ResponseEntity.noContent().build();
    }
}
