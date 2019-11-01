package ee.ituk.api.common.exception;

import ee.ituk.api.common.validation.ValidationError;
import ee.ituk.api.common.validation.ValidationResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(code= HttpStatus.BAD_REQUEST, reason = "Validation failed")
public class ValidationException extends RuntimeException{
    private List<ValidationError> errors;

    public ValidationException(ValidationResult result) {
        super();
        this.errors = result.getErrors();
    }

    public ValidationException(ValidationError error) {
        super();
        this.errors = List.of(error);
    }

}
