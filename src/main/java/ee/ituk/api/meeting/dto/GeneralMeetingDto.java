package ee.ituk.api.meeting.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class GeneralMeetingDto {
    private Long id;
    private String name;
    private LocalDate date;
    private boolean election;
    private String protocolUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
