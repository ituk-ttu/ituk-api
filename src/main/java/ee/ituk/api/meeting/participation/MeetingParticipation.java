package ee.ituk.api.meeting.participation;

import ee.ituk.api.meeting.general.GeneralMeeting;
import ee.ituk.api.user.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "meeting_participation", schema = "public")
//@Where(clause = "expires_at IS null")
public class MeetingParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private GeneralMeeting generalMeeting;
    private boolean participated;
    private boolean mandatory;
    private LocalDateTime expiresAt;
}
