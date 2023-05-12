package ru.clevertec.certificate.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.certificate.dto.ErrorDto;
import ru.clevertec.certificate.exception.ResourceNotFoundException;
import ru.clevertec.certificate.util.Constant;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> notFoundException(ResourceNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .message(exception.getMessage())
                        .errorCode(exception.getErrorCode())
                        .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorDto> notValid(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorDto.builder()
                                .message(exception.getMessage())
                                .errorCode(Constant.ERROR_CODE_VALID)
                                .build());
    }

}
