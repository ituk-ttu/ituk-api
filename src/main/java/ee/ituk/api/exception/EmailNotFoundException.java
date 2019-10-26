package ee.ituk.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "Object not found")
public class EmailNotFoundException extends UsernameNotFoundException {

    public EmailNotFoundException() {
        super("Email not found exception");
    }
}
