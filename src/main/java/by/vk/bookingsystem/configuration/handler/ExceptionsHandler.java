package by.vk.bookingsystem.configuration.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

import by.vk.bookingsystem.exception.ErrorDetails;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.exception.user.IncorrectPersonalInformationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

  private final ErrorTransformer errorTransformer;

  @Autowired
  public ExceptionsHandler(final ErrorTransformer errorTransformer) {
    this.errorTransformer = errorTransformer;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {

    final ErrorDetails errorDetails =
        new ErrorDetails(BAD_REQUEST, BAD_REQUEST.value(), ex.getMessage());

    return ResponseEntity.status(BAD_REQUEST).body(errorDetails);
  }

  @ExceptionHandler({ObjectNotFoundException.class})
  public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException ex) {

    final ErrorDetails errorDetails =
        new ErrorDetails(NOT_FOUND, NOT_FOUND.value(), ex.getMessage());

    return ResponseEntity.status(NOT_FOUND).body(errorDetails);
  }

  @ExceptionHandler({IncorrectPersonalInformationException.class})
  public ResponseEntity<Object> handleIncorrectPersonalInformationException(
      IncorrectPersonalInformationException ex) {

    final ErrorDetails errorDetails =
        new ErrorDetails(BAD_REQUEST, BAD_REQUEST.value(), ex.getMessage());

    return ResponseEntity.status(BAD_REQUEST).body(errorDetails);
  }

  @ExceptionHandler(value = {AccessDeniedException.class})
  public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex) {

    final ErrorDetails errorDetails =
        new ErrorDetails(FORBIDDEN, FORBIDDEN.value(), ex.getMessage());

    return ResponseEntity.status(FORBIDDEN).body(errorDetails);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    final String message =
        ex.getBindingResult().getAllErrors().stream()
            .map(errorTransformer)
            .collect(Collectors.joining());

    final ErrorDetails errorDetails = new ErrorDetails(status, status.value(), message);

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), ex.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest req) {

    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), ex.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex,
      @Nullable Object body,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    final ErrorDetails errorDetails =
        new ErrorDetails(status, status.value(), ex.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, headers, status);
  }
}
