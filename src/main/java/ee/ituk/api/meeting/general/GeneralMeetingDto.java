package ee.ituk.api.meeting.general;

import ee.ituk.api.meeting.agenda.MeetingAgendaDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class GeneralMeetingDto {
    private Long id;
    private LocalDate date;
    private boolean election;
    private String protocolUrl;
    private MeetingAgendaDto meetingAgenda;
    private Boolean urgent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
