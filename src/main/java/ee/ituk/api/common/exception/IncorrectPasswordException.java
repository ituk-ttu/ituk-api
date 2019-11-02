package ee.ituk.api.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.UNAUTHORIZED, reason = "Inccorect password")
public class IncorrectPasswordException extends RuntimeException {
}
