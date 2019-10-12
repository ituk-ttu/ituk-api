package ee.ituk.api.domain;

import ee.ituk.api.user.domain.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class MeetingParticipation {

  @Id
  @GeneratedValue
  private Long id;
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
  @ManyToOne
  @JoinColumn(name = "meeting_id")
  private GeneralMeeting generalMeeting;
  private boolean participated;
  private boolean mandatory;
}
