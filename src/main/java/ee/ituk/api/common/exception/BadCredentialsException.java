package ee.ituk.api.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class BadCredentialsException extends ApiException {

    public BadCredentialsException() {
        this.status = HttpStatus.UNAUTHORIZED;
        this.error = "Bad credentials";
        this.messages = List.of(ErrorMessage.builder().code("password.or.email.incorrect").build());
    }
}
