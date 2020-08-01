package ee.ituk.api.application.dto;

import lombok.Getter;

@Getter
public class ChangeApplicationStatusRequest {

    private ApplicationStatus status;

}
