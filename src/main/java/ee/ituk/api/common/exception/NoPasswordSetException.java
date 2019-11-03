package ee.ituk.api.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class NoPasswordSetException extends ApiException {

    public NoPasswordSetException() {
        this.status = HttpStatus.UNAUTHORIZED;
        this.error = "Bad credentials";
        this.messages = List.of(ErrorMessage.builder().code("password.or.email.incorrect").build());
    }
}
