package ee.ituk.api.common.exception;

import ee.ituk.api.common.validation.ValidationResult;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ValidationException extends ApiException {

    public ValidationException(ValidationResult result) {
        this.status = HttpStatus.BAD_REQUEST;
        this.error = "Validation failed";
        this.messages = result.getErrors();
    }

    public ValidationException(ErrorMessage error) {
        this.status = HttpStatus.BAD_REQUEST;
        this.error = "Validation failed";
        this.messages = List.of(error);
    }

}
