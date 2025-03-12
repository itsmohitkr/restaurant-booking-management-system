package online.devplanet.crud_application.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class CustomException extends RuntimeException {
    HttpStatus status;
    String message;

    public CustomException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


    public HttpStatus getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    @Override
    public String toString() {
        return "CustomException{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

}
