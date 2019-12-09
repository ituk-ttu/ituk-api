package ee.ituk.api.meeting.agenda;

import lombok.Data;

@Data
public class MeetingAgendaItemDto {

    private Long id;
    private String item;
    private String order;
}
