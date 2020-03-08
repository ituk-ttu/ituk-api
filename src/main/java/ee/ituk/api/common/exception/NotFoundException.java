package ee.ituk.api.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class NotFoundException extends ApiException {

    public NotFoundException() {
        this.status = HttpStatus.PRECONDITION_FAILED;
        this.error = "Object not found";
    }

    public NotFoundException(List<ErrorMessage> errorMessages) {
        this.status = HttpStatus.PRECONDITION_FAILED;
        this.error = "Object not found";
        this.messages = errorMessages;
    }
}
