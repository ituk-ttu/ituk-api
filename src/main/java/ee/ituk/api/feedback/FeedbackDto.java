package ee.ituk.api.feedback;


import lombok.Data;

import java.time.Instant;
import java.util.Objects;

@Data
public class FeedbackDto {
    private Long id;
    private String title;
    private String text;
    private Instant createdAt;

}
