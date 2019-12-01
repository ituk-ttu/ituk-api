package ee.ituk.api.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class FileStorageException extends ApiException {

    public FileStorageException() {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.error = "File storage error";
    }

    public FileStorageException(List<ErrorMessage> errorMessages) {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.error = "File storage error";
        this.messages = errorMessages;
    }
}
