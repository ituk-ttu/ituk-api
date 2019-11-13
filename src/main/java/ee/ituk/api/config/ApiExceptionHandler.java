package ee.ituk.api.config;

import ee.ituk.api.common.exception.ApiException;
import ee.ituk.api.common.exception.ErrorMessage;
import lombok.Getter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResponseEntity handleException(ApiException ex) {
        return new ResponseEntity<>(new ApiExceptionDto(ex), ex.getStatus());
    }

    @Getter
    private static class ApiExceptionDto {
        private HttpStatus status;
        private String error;
        private List<ErrorMessage> messages;

        private ApiExceptionDto(ApiException ex) {
            this.error = ex.getError();
            this.status = ex.getStatus();
            this.messages = ex.getMessages();
        }
    }

}
