package ee.ituk.api.meeting.agenda;

import lombok.Data;

import java.util.List;

@Data
public class MeetingAgendaDto {

    private Long id;
    private List<MeetingAgendaItemDto> items;
}
