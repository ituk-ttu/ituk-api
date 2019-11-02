package ee.ituk.api.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {

    public NotFoundException() {
        this.status = HttpStatus.NOT_FOUND;
        this.error = "Object not found";
    }
}
