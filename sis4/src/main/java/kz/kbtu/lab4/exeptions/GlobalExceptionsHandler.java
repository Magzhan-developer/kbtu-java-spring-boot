package kz.kbtu.lab4.exeptions;

import kz.kbtu.lab4.constants.Statuses;
import kz.kbtu.lab4.dto.CreateEventResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CreateEventResponseDto> handleValidationException(MethodArgumentNotValidException ex){
        log.error("Validation error", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body((CreateEventResponseDto.builder()
                        .status(Statuses.FAILURE.name())
                        .details("Validation error")).build());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<CreateEventResponseDto> handleResourceNotFoundException(NoHandlerFoundException ex){
        log.error("Resource not found", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                CreateEventResponseDto.builder()
                        .status(Statuses.FAILURE.name())
                        .details(ex.getMessage())
                        .build()

        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CreateEventResponseDto> handleException(Exception ex){
        log.error("Unexpected error occurred", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CreateEventResponseDto.builder()
                        .status(Statuses.UNKNOWN_ERROR.name())
                        .details("Unexpected server error")
                        .build());
    }
}
