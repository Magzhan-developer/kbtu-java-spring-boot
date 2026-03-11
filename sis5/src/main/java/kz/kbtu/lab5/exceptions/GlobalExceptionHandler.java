package kz.kbtu.lab5.exceptions;

import kz.kbtu.lab5.dto.response.BasicErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BasicErrorResponse> resourceNotFound(ResourceNotFoundException ex) {
        BasicErrorResponse response = new BasicErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BasicErrorResponse> handleAlreadyExists(IllegalArgumentException ex) {
        BasicErrorResponse response = new BasicErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicErrorResponse> handleException(Exception ex) {
        BasicErrorResponse response = new BasicErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


}
