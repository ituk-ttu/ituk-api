package ee.ituk.api.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public abstract class ApiException extends RuntimeException {

    protected HttpStatus status;
    protected String error;
    protected List<ErrorMessage> messages;
}
